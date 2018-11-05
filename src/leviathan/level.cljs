(ns leviathan.level)

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
