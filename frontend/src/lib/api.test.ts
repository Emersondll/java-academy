import assert from "node:assert/strict";
import test from "node:test";

type MemoryStorage = {
  getItem: (key: string) => string | null;
  setItem: (key: string, value: string) => void;
  removeItem: (key: string) => void;
  clear: () => void;
  key: (index: number) => string | null;
  length: number;
};

const values = new Map<string, string>();
const storage: MemoryStorage = {
  getItem: (key) => values.get(key) ?? null,
  setItem: (key, value) => {
    values.set(key, value);
  },
  removeItem: (key) => {
    values.delete(key);
  },
  clear: () => {
    values.clear();
  },
  key: () => null,
  length: 0,
};

const location = { href: "" };
Object.assign(globalThis, { localStorage: storage, window: { location } });

const { ApiError, api, clearSession, readSession, writeSession } = await import("./api.ts");

test("an old portuguese session is rewritten in english", () => {
  values.clear();
  location.href = "";
  localStorage.setItem("roteiro.sessao", JSON.stringify({
    token: "abc",
    nome: "Ana Lima",
    usuario: "ana.lima",
    modo: "TRILHA",
  }));

  const session = readSession();

  assert.equal(session?.name, "Ana Lima");
  assert.equal(session?.username, "ana.lima");
  assert.equal(session?.mode, "TRAIL");
  assert.equal(JSON.parse(localStorage.getItem("roteiro.sessao") ?? "{}").mode, "TRAIL");
});

test("a broken session is ignored", () => {
  localStorage.setItem("roteiro.sessao", "{");
  assert.equal(readSession(), null);
  localStorage.setItem("roteiro.sessao", JSON.stringify({ token: "abc" }));
  assert.equal(readSession(), null);
});

test("a saved session can be cleared", () => {
  writeSession({ token: "abc", name: "Ana", username: "ana.lima", mode: "FREE" });
  assert.equal(readSession()?.mode, "FREE");
  clearSession();
  assert.equal(readSession(), null);
});

test("a protected 401 clears the session and opens the login page", async () => {
  writeSession({ token: "abc", name: "Ana", username: "ana.lima", mode: "FREE" });
  location.href = "";
  globalThis.fetch = async () => new Response(JSON.stringify({ message: "Sign in to continue" }), { status: 401 });

  await assert.rejects(() => api("/api/dashboard"), (error: unknown) => {
    assert.ok(error instanceof ApiError);
    assert.equal(error.status, 401);
    return true;
  });
  assert.equal(location.href, "/entrar");
  assert.equal(readSession(), null);
});

test("a failed login stays on the form and keeps the client token", async () => {
  clearSession();
  localStorage.setItem("roteiro.cliente", "short");
  location.href = "";
  let sentClient = "";
  globalThis.fetch = async (_url, init) => {
    sentClient = new Headers(init?.headers).get("X-Course-Client") ?? "";
    return new Response("not-json", { status: 401, headers: { "Content-Type": "text/plain" } });
  };

  await assert.rejects(() => api("/api/auth/login", { method: "POST", body: "{}" }), (error: unknown) => {
    assert.ok(error instanceof ApiError);
    assert.equal(error.status, 401);
    assert.equal(error.message, "A operação não pôde ser concluída");
    return true;
  });
  assert.equal(location.href, "");
  assert.match(sentClient, /^[a-f0-9]{32}$/);
  assert.equal(localStorage.getItem("roteiro.cliente"), sentClient);
});

test("the same browser reuses its client token", async () => {
  let sentClient = "";
  globalThis.fetch = async (_url, init) => {
    sentClient = new Headers(init?.headers).get("X-Course-Client") ?? "";
    return new Response(JSON.stringify({ status: "ok" }), { status: 200 });
  };

  await api("/api/health");
  const first = sentClient;
  await api("/api/health");

  assert.equal(sentClient, first);
});

test("a network failure is reported without a status", async () => {
  globalThis.fetch = async () => {
    throw new Error("offline");
  };

  await assert.rejects(() => api("/api/health"), (error: unknown) => {
    assert.ok(error instanceof ApiError);
    assert.equal(error.status, 0);
    return true;
  });
});
