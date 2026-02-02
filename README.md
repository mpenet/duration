# duration

**duration** is a Clojure/ClojureScript library for parsing, and converting
duration strings and numbers to milliseconds. It enables robust handling of
human-friendly duration formats within Clojure code, supporting composition of
different time units and strict error handling.

## Features

- Parse duration strings with multiple units (e.g. `"2h30m"`)
- Accept numeric arguments as milliseconds by default
- Friendly error reporting on invalid input (throws `ExceptionInfo`)
- Supports the following units:
  - `ms`: milliseconds
  - `s`: seconds
  - `m`: minutes
  - `h`: hours
  - `d`: days
  - `w`: weeks
  - `M`: months (30 days)
  - `Y`: years (12*30 days)
- Works on both Clojure (JVM) and ClojureScript

## Installation

Add the following dependency to your `deps.edn`:

```clojure
com.s-exp/duration {:mvn/version "<version>"}
```

Or to your `project.clj`:

```clojure
[com.s-exp/duration "<version>"]
```

Replace `<version>` with the latest published version.

## Usage

Require the namespace:

```clojure
(ns myapp.core
  (:require [com.s-exp.duration :refer [duration]]))
```

Convert durations from strings or numbers:

```clojure
(duration 12345)        ;; => 12345
(duration "1s")        ;; => 1000
(duration "2h30m")     ;; => 9000000 (2 hours + 30 minutes)
(duration "1d1h1m1s")  ;; => 90061000
```

### Invalid Input

If input is not a valid duration (unrecognized units, negative value, etc.), an `ExceptionInfo` is thrown:

```clojure
(try
  (duration "1x")
  (catch clojure.lang.ExceptionInfo e
    (ex-message e)))
;; => "Invalid duration value 1x"
```

### Supported Units Table

| Unit | Meaning        | Example | Milliseconds |
|------|---------------|---------|--------------|
| ms   | Milliseconds  | 500ms   | 500          |
| s    | Seconds       | 2s      | 2000         |
| m    | Minutes       | 1m      | 60000        |
| h    | Hours         | 1h      | 3600000      |
| d    | Days          | 1d      | 86400000     |
| w    | Weeks         | 1w      | 604800000    |
| M    | Months (30d)  | 1M      | 2592000000   |
| Y    | Years (360d)  | 1Y      | 31104000000  |

## API

### `(duration x)`

- **x**: `Number` (milliseconds assumed), or `String` (e.g., `"1h30m"`, any supported unit combination)
- Returns milliseconds as an integer
- Throws `ExceptionInfo` on error

### `(to-ms unit x)`

- **unit**: One of the unit strings (see table).
- **x**: Number.
- Converts a given number for a unit to milliseconds.

## Development & Testing

Run tests using:

```shell
clojure -T:build test
```

## License

Distributed under the EPL-1.0 license. See [LICENSE](LICENSE).
