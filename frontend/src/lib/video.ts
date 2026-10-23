const YOUTUBE_ID = /^[A-Za-z0-9_-]{11}$/;

export function youtubeId(url: string): string | null {
  let endereco: URL;
  try {
    endereco = new URL(url);
  } catch {
    return null;
  }
  if (!youtubeHost(endereco.hostname)) {
    return null;
  }
  const porParametro = endereco.searchParams.get("v");
  if (porParametro && YOUTUBE_ID.test(porParametro)) {
    return porParametro;
  }
  const partes = endereco.pathname.split("/").filter((parte) => parte.length > 0);
  if (partes.length === 0) {
    return null;
  }
  const host = endereco.hostname.replace(/^www\./, "");
  if (host === "youtu.be" && YOUTUBE_ID.test(partes[0])) {
    return partes[0];
  }
  const marcador = partes.findIndex(
    (parte) => parte === "embed" || parte === "shorts" || parte === "live" || parte === "v",
  );
  const id = marcador >= 0 ? partes[marcador + 1] : undefined;
  if (id && YOUTUBE_ID.test(id)) {
    return id;
  }
  return null;
}

export function httpsVideo(url: string): string | null {
  try {
    const endereco = new URL(url);
    if (endereco.protocol !== "https:" || youtubeHost(endereco.hostname)) {
      return null;
    }
    return url;
  } catch {
    return null;
  }
}

function youtubeHost(hostname: string): boolean {
  const host = hostname.replace(/^www\./, "");
  return host === "youtu.be"
    || host === "youtube.com"
    || host.endsWith(".youtube.com")
    || host === "youtube-nocookie.com"
    || host.endsWith(".youtube-nocookie.com");
}
