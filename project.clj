(defproject catalysis "1.0.0"
  :description "On opinionated (+ clj cljs datomic datascript (reagent react) web development framework" ;;should this be "an" or "un"?
  ;; Change to catalysis.io eventually...
  :url "https://github.com/metasoarous/catalysis"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145"]
                 [org.clojure/core.async "0.2.371"]
                 [com.stuartsierra/component "0.3.0"]
                 [environ "1.0.1"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [org.clojure/tools.logging "0.3.1"]
                 [slingshot "0.12.2"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [compojure "1.4.0"]
                 [http-kit "2.1.19"]
                 [bidi "1.21.1"]
                 [com.taoensso/sente "1.6.0" :exclusions [org.clojure/tools.reader]]
                 [com.cognitect/transit-clj "0.8.285" :exclusions [commons-codec]]
                 [com.cognitect/transit-cljs "0.8.225"]
                 [com.lucasbradstreet/cljs-uuid-utils "1.0.2"]
                 [testdouble/clojurescript.csv "0.2.0"]
                 [reagent "0.5.1"]
                 [re-frame "0.5.0"]
                 [org.webjars/bootstrap "3.3.5"]
                 [posh "0.3.5"]
                 [re-com "0.8.0"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [prismatic/schema "1.1.1"]
                 [org.clojure/core.typed "0.3.23"]
                 [datsync "0.0.1-SNAPSHOT"]
                 [datascript "0.13.3"]
                 [io.rkn/conformity "0.4.0"]
                 ;;For the free version of Datomic
                 [com.datomic/datomic-free "0.9.5350"
                  :exclusions [joda-time]]]
  :plugins [[lein-cljsbuild "1.1.0"]]
  ;; For Datomic Pro uncomment the following and set the DATOMIC_USERNAME and DATAOMIC_PASSWORD environment
  ;; variables of the process in which you run this program to those matching your Datomic Pro account. You'll
  ;; have to start your own transactor separately from this process as well. More instructions on how to do
  ;; that in the Wiki (I think... bug us if you can't find them).
  ;:repositories {"my.datomic.com" {:url
  ;                               "https://my.datomic.com/repo"
  ;                                 :username
  ;                                [:env/datomic_username]
  ;                                 :password
  ;                                 [:env/datomic_password]}}
  :source-paths ["src"]
                 ;; Can uncomment to test development of datsync or datview (must also check out said projects
                 ;; in libs for this to work)
                 ;"libs/datsync/src"
                 ;"libs/datview/src"]
  :resource-paths ["resources" "resources-index/prod"]
  :target-path "target/%s"
  :main ^:skip-aot catalysis.run
  :repl-options {:init-ns user}
  :cljsbuild {:builds {:client {:source-paths ["src/catalysis/client" "src/datview"]
                                :compiler {:output-to "resources/public/js/app.js"
                                           :output-dir "dev-resources/public/js/out"}}
                       :devcards {:source-paths ["src"]
                                  :figwheel {:devcards true}  ;; <- note this
                                  :compiler {:main    "catalysis.client.cards"
                                             :asset-path "js/compiled/devcards_out"
                                             :output-to  "resources/public/js/catalysis_devcards.js"
                                             :output-dir "resources/public/js/devcards_out"
                                             :source-map-timestamp true}}}}
  :figwheel {:server-port 3448
             :repl true}
  :profiles {:dev-config {}
             :dev [:dev-config
                   {:dependencies [[alembic "0.3.2"]
                                   [figwheel "0.5.0-3"]
                                   [devcards "0.2.1"]]
                    :plugins [[lein-figwheel "0.3.9" :exclusions [org.clojure/clojure org.clojure/clojurescript org.codehaus.plexus/plexus-utils]]
                              [com.palletops/lein-shorthand "0.4.0"]
                              [lein-environ "1.0.1"]]
                    ;; The lein-shorthand plugin gives us access to the following shortcuts as `./*` (e.g. `./pprint`)
                    :shorthand {. [clojure.pprint/pprint
                                   alembic.still/distill
                                   alembic.still/lein
                                   taoensso.timbre/trace
                                   taoensso.timbre/spy]}
                    :source-paths ["dev"]
                    :resource-paths ^:replace ["resources" "dev-resources" "resources-index/dev"]
                    :cljsbuild
                    {:builds
                     {:client {:source-paths ["dev"]
                               :compiler
                               {:optimizations :none
                                :source-map true}}}}}]
             :prod {:cljsbuild
                    {:builds
                     {:client {:compiler
                               {:optimizations :advanced
                                :pretty-print false}}}}}}
  :aliases {"package"
            ["with-profile" "prod" "do"
             "clean" ["cljsbuild" "once"]]})

