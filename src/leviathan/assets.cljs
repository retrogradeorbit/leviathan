(ns leviathan.assets)

(def tiles
  {
   :one {:pos [0 0] :size [32 32]}
   })

(def tile-mapping
  {
   :exit-up [0 0]
   :exit-down [32 0]
   :button-blue [64 0]
   :door-blue [96 0]
   :hashed-floor-blue [128 0]
   :floor [(+ 128 32 +32) 32]
   :wall-back [64 64]
   :wall-front [0 96]
   :wall-front-2 [32 96]
   :wall-front-3 [64 96]
   })
