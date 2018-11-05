(ns leviathan.level
  (:require ["rot-js/dist/rot.js" :as rot]))

(def test-map-src
  [
   "             XXXXXXXXXXXXXXX     "
   "   XZXXXX    X.............X     "
   "   X....XX XXX.............X     "
   "   X.....XYX...X...........X     "
   "   X.........XXXX..........X     "
   "   X....XXXXXX  X..........X     "
   "   X....X       X..........X     "
   "   XXX.XX       XXXXX...XXXX     "
   "     X.X            X...X        "
   "     X.X            X..XX        "
   "  XXXX.XXXXXXXXXXXX X..X         "
   "  X...............XXX..X         "
   "  X....................X         "
   "  X....................X         "
   "  X...............XXXXXX         "
   "  X...............X              "
   "  XXXXXXXXXXXXXXXXX              "

   ])

(def test-map
  (mapv
   (fn [line] (mapv {"." :floor
                     "X" :wall-front
                     "Y" :wall-front-2
                     "Z" :wall-front-3
                     }
               line))
   test-map-src))

(defn get-wall-type [maptiles x y]
  (when
    (some true?
      (map
        (fn [[ox oy]]
          (= (get-in maptiles [(+ x ox) (+ y oy)]) 0))
        [[-1 -1] [0 -1] [1 -1]
         [-1  0]        [1  0]
         [-1  1] [0  1] [1 1]]))
    :wall-front))

(defn make-map []
  (let [rotmap (rot/Map.Rogue. 64 64 (clj->js {:cellWidth 5
                                               :cellHeight 6}))]
    (.create rotmap)
    (let [maptiles (.-map rotmap)]
      (into [] (map-indexed
                 (fn [y line]
                   (into []
                         (map-indexed (fn [x c]
                                        ({1 (get-wall-type maptiles y x)
                                          0 :floor} c))
                                      line)))
                 maptiles)))))
