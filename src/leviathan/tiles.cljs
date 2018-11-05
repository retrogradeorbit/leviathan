(ns leviathan.tiles
  (:require [infinitelives.pixi.tilemap :as tm]
            [infinitelives.pixi.sprite :as s]
            [infinitelives.pixi.canvas :as c]
            [infinitelives.pixi.pixelfont :as pf]

            [infinitelives.utils.events :as e]
            [infinitelives.utils.vec2 :as vec2]
            [infinitelives.utils.spatial :as spatial]

            [leviathan.assets :as assets]
            [leviathan.level :as level]
            ;;[leviathan.state :as state]
            [leviathan.controls :as controls]
            [cljs.core.async :refer [timeout]])
  (:require-macros [cljs.core.async.macros :refer [go]]
                   ;;[infinitelives.pixi.macros :as m]
                   [infinitelives.pixi.pixelfont :as pf]))

(def scale 1)
(def size 64)

(defn run []
  (go
    ;;(swap! state/state assoc :running? false)
    (let [tile-set (tm/make-tile-set :tilesheet assets/tile-mapping [size size])
          tile-map level/test-map

          tile-results (tm/make-tile-sprites tile-set tile-map)
          tile-sprites (mapv second tile-results)
          tile-locations (into #{} (mapv first tile-results))

          map-sprite-set (s/make-container :children tile-sprites
                                  :scale scale)]
      (c/with-sprite :bg
        [level-map map-sprite-set]
        (c/with-sprite :ui
          [text (pf/make-text :small "Press Space Or Button To Start"
                              :scale scale :x 0 :y 0
                              :visible true)]

          (loop [theta 0]
            (let [[x y] (vec2/get-xy
                         (vec2/add
                          (vec2/vec2 -750 -500)
                          (vec2/rotate (vec2/vec2 100 0) theta )))]
              (s/set-pos! level-map (vec2/vec2 (int x) (int y)))
              (<! (e/next-frame))
              (when-not (controls/fire?)
                (recur (+ theta 0.01)))))

          )))))
