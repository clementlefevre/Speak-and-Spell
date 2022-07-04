(ns speak-n-spell.sound-service
  (:require))

(defn create-audio-elements [urls]
  (for [url  urls]
    (let [s (.createElement js/document "audio")]
      (set! (.-src s) url)
      (set! (.-preload s) "auto")
      s)))


; https://clojurians.slack.com/archives/C03S1L9DN/p1580496131034600
(defn play-audio [audio]
  (println "playing :" (.-src audio))
  (js/setTimeout #(.play audio) 500)
 ; (.play audio)
  (js/Promise.
   (fn [resolve reject]
     (.addEventListener audio "ended" resolve))))

(defn remove-audio [audio]
  (.remove  audio)
  (println "removed " (.-src  audio)))

(defn playback-mp3 [sounds]
  (println "count sounds :" (count sounds))
  (-> (play-audio (first sounds))
      (.then (fn [] (remove-audio (first sounds))
               (let [rest-sounds  (rest sounds)]
                 (println "rest sounds :" (count rest-sounds))
                 (if (= 0 (count rest-sounds))
                   (remove-audio (first sounds))
                   (playback-mp3 rest-sounds)))))))

(defn play-list [urls]
  (let [sounds  (create-audio-elements urls)]
    (playback-mp3 sounds)))







; 0686989296
; open 10go sim
; 3900 - 06 86 98 