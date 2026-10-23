import assert from "node:assert/strict";
import test from "node:test";
import { httpsVideo, youtubeId } from "./video.ts";

const ID = "dQw4w9WgXcQ";

test("youtube links from the usual pages resolve to the video id", () => {
  assert.equal(youtubeId(`https://www.youtube.com/watch?v=${ID}`), ID);
  assert.equal(youtubeId(`https://m.youtube.com/watch?v=${ID}`), ID);
  assert.equal(youtubeId(`https://music.youtube.com/watch?v=${ID}`), ID);
  assert.equal(youtubeId(`https://youtu.be/${ID}`), ID);
  assert.equal(youtubeId(`https://www.youtube.com/embed/${ID}`), ID);
  assert.equal(youtubeId(`https://www.youtube.com/shorts/${ID}`), ID);
  assert.equal(youtubeId(`https://www.youtube.com/live/${ID}`), ID);
  assert.equal(youtubeId(`https://www.youtube.com/v/${ID}`), ID);
  assert.equal(youtubeId(`https://www.youtube-nocookie.com/embed/${ID}`), ID);
  assert.equal(youtubeId(`https://m.youtube-nocookie.com/embed/${ID}`), ID);
});

test("a youtube page without an id is not treated as a file", () => {
  assert.equal(youtubeId("https://www.youtube.com/feed/library"), null);
  assert.equal(youtubeId("https://www.youtube.com/"), null);
  assert.equal(httpsVideo(`https://www.youtube.com/shorts/${ID}`), null);
  assert.equal(httpsVideo("https://cdn.example.com/aula.mp4"), "https://cdn.example.com/aula.mp4");
  assert.equal(httpsVideo("http://cdn.example.com/aula.mp4"), null);
  assert.equal(httpsVideo("notaurl"), null);
  assert.equal(youtubeId("notaurl"), null);
  assert.equal(youtubeId("https://videos.example.com/aula.mp4"), null);
});
