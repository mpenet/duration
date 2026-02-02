(ns s-exp.duration
  "Utilities for parsing and converting durations from strings or numbers to milliseconds.
  Supports parsing multiple time units (e.g., \"2h30m\") and throws when inputs
  are invalid")

(def second-ms 1000)
(def minute-ms (* second-ms 60))
(def hour-ms (* minute-ms 60))
(def day-ms (* 24 hour-ms))
(def week-ms (* 7 day-ms))
(def month-ms (* 30 day-ms))
(def year-ms (* 12 month-ms))

(defn to-ms
  "Converts a value to milliseconds based on the given time unit.
  Parameters:
  - unit: a string, one of \"ms\", \"s\", \"m\", \"h\", \"d\", \"w\", \"M\", \"Y\".
  - x: numeric value for the unit.
  Returns the corresponding number of milliseconds."
  [unit x]
  (* x
     (case unit
       "ms" 1
       "s" second-ms
       "m" minute-ms
       "h" hour-ms
       "d" day-ms
       "w" week-ms
       "M" month-ms
       "Y" year-ms)))

(defn ex-invalid!
  "Throws an ex-info exception indicating an invalid duration input."
  [x & [e]]
  (throw
   (ex-info (str "Invalid duration value '" x "'")
            {:val x
             :type :s-exp.duration/invalid}
            e)))

(defn duration
  "Converts a numeric or string value into a duration in milliseconds.

  Accepts:
  - Strings such as: \"1ms\", \"2h30m\", etc. (composing multiple values)
  - Numeric param value is interpreted as milliseconds directly.

  Supported units:
  - ms: milliseconds
  - s: seconds
  - m: minutes
  - h: hours
  - d: days
  - w: weeks
  - M: months (30 days)
  - Y: years (12*30 days)

  Throws if the input is invalid."
  [x]
  (try
    (cond
      (string? x)
      (or (some->> x
                   (re-seq #"([0-9]+)([mshdwMY]+)")
                   (reduce (fn [d [_ x u]]
                             (+ d (to-ms u
                                         (#?(:clj Long/parseLong :cljs js/parseInt) x))))
                           0))
          (ex-invalid! x nil))

      (nat-int? x) x
      :else (ex-invalid! x nil))
    (catch #?(:clj Exception :cljs :default) e
      (ex-invalid! x e))))
