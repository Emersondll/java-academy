export const API = process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080";

const STORAGE_KEY = "roteiro.sessao";

export type Mode = "FREE" | "TRAIL";

export type Session = {
  token: string;
  name: string;
  username: string;
  mode: Mode;
};

const CLIENT_KEY = "roteiro.cliente";
const CLIENT_TOKEN = /^[A-Za-z0-9_-]{16,64}$/;

export class ApiError extends Error {
  readonly status: number;
  readonly openLessonId?: number;

  constructor(message: string, status: number, openLessonId?: number) {
    super(message);
    this.status = status;
    this.openLessonId = openLessonId;
  }
}

export function readSession(): Session | null {
  if (typeof window === "undefined") {
    return null;
  }
  const raw = localStorage.getItem(STORAGE_KEY);
  if (!raw) {
    return null;
  }
  try {
    const parsed = JSON.parse(raw) as Session & { nome?: string; usuario?: string; modo?: string };
    const mode = parsed.mode ?? (parsed.modo === "TRILHA" ? "TRAIL" : parsed.modo === "LIVRE" ? "FREE" : undefined);
    if (!parsed.token || !mode) {
      return null;
    }
    const session: Session = {
      token: parsed.token,
      name: parsed.name ?? parsed.nome ?? "",
      username: parsed.username ?? parsed.usuario ?? "",
      mode,
    };
    localStorage.setItem(STORAGE_KEY, JSON.stringify(session));
    return session;
  } catch {
    return null;
  }
}

export function writeSession(session: Session) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(session));
}

export function clearSession() {
  localStorage.removeItem(STORAGE_KEY);
}

export async function api<T>(path: string, options: RequestInit = {}): Promise<T> {
  const session = readSession();
  const headers = new Headers(options.headers);
  if (options.body) {
    headers.set("Content-Type", "application/json");
  }
  if (session?.token) {
    headers.set("Authorization", `Bearer ${session.token}`);
  }
  const client = clientToken();
  if (client) {
    headers.set("X-Course-Client", client);
  }

  let response: Response;
  try {
    response = await fetch(`${API}${path}`, { ...options, headers });
  } catch {
    throw new ApiError("Não foi possível falar com a API", 0);
  }

  if (response.status === 401 && !path.startsWith("/api/auth/")) {
    clearSession();
    window.location.href = "/entrar";
    throw new ApiError("Faça login para continuar", 401);
  }

  if (!response.ok) {
    const body = (await response.json().catch(() => null)) as { message?: string; openLessonId?: number } | null;
    throw new ApiError(body?.message ?? "A operação não pôde ser concluída", response.status, body?.openLessonId);
  }

  return (await response.json()) as T;
}

function clientToken(): string | null {
  if (typeof window === "undefined") {
    return null;
  }
  const current = localStorage.getItem(CLIENT_KEY);
  if (current && CLIENT_TOKEN.test(current)) {
    return current;
  }
  const created = crypto.randomUUID().replaceAll("-", "");
  localStorage.setItem(CLIENT_KEY, created);
  return created;
}
