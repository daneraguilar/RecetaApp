# RecetaApp
## App multi module

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Pratice multi module app,

- Kotlin
- Clean architecture
- MVVM
- Unit test
- Coroutines
- Livedata
- Retrofit
- Navigation
- koin
- ✨Magic ✨

## Features

- Get recipes list
- Get recipe by uuid
- google map

Context

> La app es una práctica para implementar un ejemplo de una app multi módulo durante mi experiencia a medida que crece las aplicaciones,
los feature van creciendo dejándonos un monolito muy difícil de manejar que viene a solucionar esta forma de dividir los feature en módulo independientes,
mezclando con clean architecture tenemos una app muy escalable. Cabe recalcar que faltaron algunas implementaciones de manejos de versions.gragle, paginar la repuesta, pero no logre hacerlo con mockable.io sonar,
el pipe en Circle o bitrise, y R8 mejoras de optimización de la app me quede un poco corto de tiempo, ya que actualmente me encuentro laborando actualmente.

>En módulo de common se decidió agregar la base que todos los módulos necesitarían en los módulos, se decide Crear un tipo repuesta de Result generales para los caso y repositorio los cuales
pueden ser extensibles para cada módulo si así lo necesitan, en el viewModule se crean tipo de estados con una sealed class para no tener una cantida elevada de live data en el fragment,
esto me permite tener una sintaxis muy clara y manteniendo el mutable privado para el único tenga opción de cambiar el estado sea el view model, en la ui re respetan los harcodeados de String y dimisiones extrayéndolos
y usando tool:text para que se pueda visualizar en pantalla y no se escape un texto harcodeado, se utilizó constrainlayout teniendo él cuenta que es la heredera de Relativelayout mucho más optimizada descartando
la penalización que tenemos en vista anidadas.
Al ser un app simple se realizan Test necesario si se observa la facilidad que se puede obtener Aplicando SOLID

Muchas gracias seria bueno un feedback de este modo voy mejerando!!