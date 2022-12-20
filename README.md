![example workflow](https://github.com/ReynaX/JSON-tools/actions/workflows/ci.yml/badge.svg)
# JSON-tools
Aplikacja dla programistów, którzy potrzebują przeformatować lub filtrować struktury danych zapisane w formacie JSON a także porównać ze soba struktury. JSON tools pozwala zarówno na zminifikowanie niezminifikowanej reprezentacji JSON, a także na operację odwrotną (z dodaniem wszelkich odstępów i nowych linii). Aplikacja będzie dostępna poprzez GUI, a także jako zdalne API, dzieki czemu można ją zintegrować z istniejącymi narzędziami.

## JSON-tools API

Poniższe przykłady zostały zrealizowane za pomocą [curl](https://curl.se/download.html]).

### Minify

#### Request
<code>POST minify</code>
<pre><code> curl -X POST localhost:8080/json-tools/minify -H "Content-Type: application/json" -d "{\"json\" : {\"option\": \"option\", \"option2\": 123}}"</code></pre>

#### Response
<pre><code>{"option":"option","option2":123}</code></pre>

### Prettify

#### Request
<code>POST prettify</code>
<pre><code>curl -X POST localhost:8080/json-tools/prettify -H "Content-Type: application/json" -d "{\"json\" : {\"option\": \"option\", \"option2\": 123}}"</code></pre>

#### Response
<pre><code>{
  "option" : "option",
  "option2" : 123
}
</code></pre>

### Filter

#### Request
<code>POST filter</code>
<pre><code>curl -X POST localhost:8080/json-tools/filter -H "Content-Type: application/json" -d "{\"json\" : {\"option\": \"option\", \"option2\": 123}, \"keys\
": [\"option\"]}"</code></pre>

#### Response
<pre><code>{
  "option" : "option"
}
</code></pre>

### Extract

#### Request
<code>POST extract</code>
<pre><code>curl -X POST localhost:8080/json-tools/extract -H "Content-Type: application/json" -d "{\"json\" : {\"option\": \"option\", \"option2\": 123}, \"keys\
": [\"option\"]}"</code></pre>

#### Response
<pre><code>{
  "option2" : 123
}
</code></pre>

### Compare

#### Request
<code>POST compare</code>
<pre><code>curl -X POST localhost:8080/json-tools/compare -H "Content-Type: application/json" -d "{\"json1\" : {\"option\": \"option\", \"option2\": 123}, \"json2\": {\"option\": \"option_changed\"}}"</code></pre>

#### Response
<pre><code>[ {
  "op" : "replace",
  "path" : "/option",
  "value" : "option_changed"
}, {
  "op" : "remove",
  "path" : "/option2"
} ]
</code></pre>

### UML Diagram
![UML class](https://user-images.githubusercontent.com/41871003/208314325-1b6a39b8-f0f5-4d0b-9572-ea85b94afb03.png)
![UML class (1)](https://user-images.githubusercontent.com/41871003/208314328-7049e0ee-a731-4c84-9c38-dadcac9ebb21.png)

### Authors
- [Przemysław Marcinkowski](https://github.com/ReynaX)
- [Andrei Staravoitau](https://github.com/Anstari)
- [Maciej Matysiak](https://github.com/CaisyJeicam)
