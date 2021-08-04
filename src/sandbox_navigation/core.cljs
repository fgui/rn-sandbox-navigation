(ns sandbox-navigation.core
  (:require ["react-native-gesture-handler"]
            [steroid.rn.core :as rn]
            [re-frame.core :as re-frame]
            [steroid.rn.navigation.core :as rnn]
            [steroid.rn.navigation.stack :as stack]
            [steroid.rn.navigation.bottom-tabs :as bottom-tabs]
            [sandbox-navigation.views :as screens]
            [steroid.rn.navigation.safe-area :as safe-area]
            steroid.rn.navigation.events
            sandbox-navigation.events
            sandbox-navigation.subs

            ["@react-navigation/drawer" :refer (createDrawerNavigator)]
            [reagent.core :as reagent]
            [steroid.rn.utils :as utils]
            ))

(defn create-drawer-navigator []
  (let [^js drawer (createDrawerNavigator)]
    [(reagent/adapt-react-class (.-Navigator drawer))
     (reagent/adapt-react-class (.-Screen drawer))]))

(defn drawer [& params]
   (let [[navigator screen] (create-drawer-navigator)]
     (utils/prepare-navigator navigator screen)))

(defn main-screens []
  [bottom-tabs/bottom-tab
   [{:name      :home
     :component screens/home-screen}
    {:name      :basic
     :component screens/basic-screen}
    {:name      :ui
     :component screens/ui-screen}
    {:name      :list
     :component screens/list-screen}
    {:name      :storage
     :component screens/storage-screen}]])

(defn main-drawer []
  [drawer {:initial-route-name :basic}
   [{:name      :home
     :component screens/home-screen}
    {:name      :basic
     :component screens/basic-screen}
    {:name      :ui
     :component screens/ui-screen}
    {:name      :list
     :component screens/list-screen}
    {:name      :storage
     :component screens/storage-screen}]])

(defn root-stack []
  [safe-area/safe-area-provider
   [(rnn/create-navigation-container-reload
     {:on-ready #(re-frame/dispatch [:init-app-db])}
     [stack/stack {:mode :modal :header-mode :none}
      [{:name      :main
        :component main-drawer}
       {:name      :modal
        :component screens/modal-screen}]])]])

(defn init []
  (rn/register-comp "SandboxNavigation" root-stack))
