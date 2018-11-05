(ns leviathan.assets)

(def ts 64)

(def tiles
  {
   :one {:pos [0 0] :size [ts ts]}
   })

(def tile-mapping
  {
   :exit-up [0 0]
   :exit-down [ts 0]
   :button-blue [(* ts 2) 0]
   :door-blue [(* ts 3) 0]
   :hashed-floor-blue [(* ts 4) 0]
   :floor [(+ (* ts 4) ts ts) ts]
   :wall-front [0 (* ts 3)]
   :wall-front-2 [ts (* ts 3)]
   :wall-front-3 [(* ts 2) (* ts 3)]
   })
