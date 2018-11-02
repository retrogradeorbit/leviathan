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

(def scale 2)

(defn run []
  (go
    ;;(swap! state/state assoc :running? false)
    (let [tile-set (tm/make-tile-set :tilesheet assets/tile-mapping [32 32])
          [floor-tile-map wall-tile-map] [level/test-map level/wall-backs]

          floor-tile-results (tm/make-tile-sprites tile-set floor-tile-map)
          floor-tile-sprites (mapv second floor-tile-results)
          floor-tile-locations (into #{} (mapv first floor-tile-results))
          wall-tile-sprites (mapv second (tm/make-tile-sprites tile-set wall-tile-map))

          floor (s/make-container :children floor-tile-sprites
                                  :scale scale)

          walls (s/make-container :children wall-tile-sprites
                                  :scale scale
                                  :y (* scale -32))]
      (c/with-sprite :bg
        [container (s/make-container :children [floor walls] :scale 1)]
        (c/with-sprite :ui
          [text (pf/make-text :small "Press Space Or Button To Start"
                              :scale scale :x 0 :y 0
                              :visible true)]

          (loop [theta 0]
            (let [[x y] (vec2/get-xy
                         (vec2/add
                          (vec2/vec2 -750 -500)
                          (vec2/rotate (vec2/vec2 100 0) theta )))]
              (s/set-pos! container (vec2/vec2 (int x) (int y)))
              (<! (e/next-frame))
              (when-not (controls/fire?)
                (recur (+ theta 0.01)))))

          )))))
