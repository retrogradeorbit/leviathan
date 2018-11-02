(ns leviathan.core
  (:require [infinitelives.pixi.canvas :as c]
            [infinitelives.pixi.resources :as r]
            [infinitelives.pixi.texture :as t]
            [infinitelives.pixi.tilemap :as tm]
            [infinitelives.pixi.sprite :as s]
            [infinitelives.pixi.pixelfont :as pf]
            [infinitelives.utils.events :as e]
            [infinitelives.utils.vec2 :as vec2]
            [infinitelives.utils.gamepad :as gp]
            [infinitelives.utils.pathfind :as path]
            [infinitelives.utils.console :refer [log]]
            [infinitelives.utils.sound :as sound]

            [leviathan.assets :as assets]
            )
  (:require-macros [cljs.core.async.macros :refer [go]]
                   ;;[infinitelives.pixi.canvas :as c]
                   [infinitelives.pixi.pixelfont :as pf]
                   [infinitelives.utils.async :as async]
                   )
  )

(enable-console-print!)

(println "This text is printed from src/leviathan/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"
                          :__figwheel_counter 0}))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  (swap! app-state update-in [:__figwheel_counter] inc)
  )

(defonce bg-colour 0x2F283A)

(defonce canvas
  (c/init {:layers [:bg :ocean :player :clouds :damage :score :ui :top-text]
           :background bg-colour
           :expand true
           :origins {:top-text :top
                     :damage :bottom-right
                     :score :bottom-left}}))

(def scale 3)

(defn make-background []
  (let [bg (js/PIXI.Graphics.)
        border-colour 0x000000
        width 32
        height 32
        full-colour 0xff0000
        ]
    (doto bg
      (.beginFill 0xff0000)
      (.lineStyle 0 border-colour)
      (.drawRect 0 0 width height)
      (.lineStyle 0 border-colour)
      (.beginFill full-colour)
      (.drawRect 0 0 32 32)
      .endFill)
    (.generateTexture bg false)))

(defonce main
  (go                              ;-until-reload
                                        ;state
                                        ; load resource url with tile sheet
    (<! (r/load-resources canvas :ui ["img/tilesheet.png"
                                      "img/font.png"

                                      ]))

    (t/load-sprite-sheet!
     (r/get-texture :tilesheet :nearest)
     assets/tiles)

    (pf/pixel-font :small "img/font.png" [11 117] [235 169]
                   :chars ["ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                           "abcdefghijklmnopqrstuvwxyz"
                           "0123456789!?#`'.,-"]
                   :kerning {"fo" -2  "ro" -1 "la" -1 }
                   :space 5)

    (loop []
      (<! (let [load-num (:__figwheel_counter @app-state)]
            (async/go-while (= load-num (:__figwheel_counter @app-state))
                            (console.log "making sprite")
                            (c/with-sprite canvas :bg
                              [sprite (s/make-sprite :one)]

                              (loop []
                                (<! (e/next-frame))
                                (recur))))))
      (recur))))
