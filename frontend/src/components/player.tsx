import { httpsVideo, youtubeId } from "@/lib/video";

export function Player({ url, titulo }: { url: string | null; titulo: string }) {
  const youtube = url ? youtubeId(url) : null;
  if (youtube) {
    return (
      <div className="tela">
        <iframe
          src={`https://www.youtube-nocookie.com/embed/${youtube}`}
          title={titulo}
          referrerPolicy="strict-origin-when-cross-origin"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowFullScreen
        />
      </div>
    );
  }
  const seguro = url ? httpsVideo(url) : null;
  if (seguro) {
    return (
      <div className="tela">
        <video src={seguro} controls playsInline />
      </div>
    );
  }
  return (
    <div className="tela tela-vazia">
      <span className="anel" aria-hidden="true" />
      <p>Vídeo deste tema</p>
      <strong>{titulo}</strong>
    </div>
  );
}
