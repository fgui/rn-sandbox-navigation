(ns sandbox-navigation.core
  (:require [steroid.rn.core :as rn]))

(defn root-comp []
  [rn/view
   [rn/text "Hello CLojure! from CLJS"]])

(defn init []
  (rn/register-reload-comp "SandboxNavigation" root-comp))

