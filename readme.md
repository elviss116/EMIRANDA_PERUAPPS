# APPLICACION COMPARTIR LUGARES

La siguiente aplicacion permite a las personas compartir y visualizar los lugares compartidos por otras personas, esta desarrollado en Kotlin.

### Arquitectura

El siguiente diagrama representa la arquitecura usada, la unica diferencia es que la fuente externa de datos es Firebase, la cual es almacenada en Room y los datos mostrados al usuario son cargados directamente desde Room

[![MVVM](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/79527352_1023584987982353_4955120682976411648_n.png?_nc_cat=105&ccb=2&_nc_sid=ae9488&_nc_ohc=2F5jRqRjavIAX-Hyy_D&_nc_ht=scontent.flim23-1.fna&oh=9b8a2f11d7f6979ff833b05cc2d15e8a&oe=5FD04566 "MVVM")](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/79527352_1023584987982353_4955120682976411648_n.png?_nc_cat=105&ccb=2&_nc_sid=ae9488&_nc_ohc=2F5jRqRjavIAX-Hyy_D&_nc_ht=scontent.flim23-1.fna&oh=9b8a2f11d7f6979ff833b05cc2d15e8a&oe=5FD04566 "MVVM")

#### Firebase FireStore

La fuente de datos usada es FireStore , ya que a diferencia de realtime , firestore es mas escalable y tiene multiples opciones para filtrar los datos.

Los datos de los lugares compartidos estan almacenados en la coleccion Places

[![place-firebase](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124088015_394323938383460_6575054530510036410_n.png?_nc_cat=103&ccb=2&_nc_sid=ae9488&_nc_ohc=C4U4NNpYyAkAX94gKPQ&_nc_ht=scontent.flim23-1.fna&oh=ab547f5e125e24b330b843afe843faad&oe=5FCECE8A "place-firebase")](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124088015_394323938383460_6575054530510036410_n.png?_nc_cat=103&ccb=2&_nc_sid=ae9488&_nc_ohc=C4U4NNpYyAkAX94gKPQ&_nc_ht=scontent.flim23-1.fna&oh=ab547f5e125e24b330b843afe843faad&oe=5FCECE8Ahttp:// "place-firebase")

Adicionalmente, cada 30 minutos se almacena la ubicacion de los usuarios, la cual se encuentra en la collecion USERAPP

[![user-fir](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124311714_659774558040729_3288939974391491110_n.png?_nc_cat=110&ccb=2&_nc_sid=ae9488&_nc_ohc=Pec7Lt6a6gsAX-PY5Ri&_nc_ht=scontent.flim23-1.fna&oh=200fa36006e0907e06491cdc614f5736&oe=5FCFF65A "user-fir")](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124311714_659774558040729_3288939974391491110_n.png?_nc_cat=110&ccb=2&_nc_sid=ae9488&_nc_ohc=Pec7Lt6a6gsAX-PY5Ri&_nc_ht=scontent.flim23-1.fna&oh=200fa36006e0907e06491cdc614f5736&oe=5FCFF65A "user-fir")

####Firebase Storage

Las imagenes subidas por los usuarios son almacenadas en el directorio /images

[![storage](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124676159_2738425026422745_7671318222559713622_n.png?_nc_cat=100&ccb=2&_nc_sid=ae9488&_nc_ohc=Dw6Bj8bAt00AX8xuZH_&_nc_ht=scontent.flim23-1.fna&oh=38458cdb93eb7888f07e7f21dfc427ff&oe=5FD0526A "storage")](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124676159_2738425026422745_7671318222559713622_n.png?_nc_cat=100&ccb=2&_nc_sid=ae9488&_nc_ohc=Dw6Bj8bAt00AX8xuZH_&_nc_ht=scontent.flim23-1.fna&oh=38458cdb93eb7888f07e7f21dfc427ff&oe=5FD0526A "storage")

#### KOTLIN FLOW

Se uso flow para poder hacer una escucha en tiempo real de los datos que son agregados al collection "places" de firebase. 
Cada vez que un nuevo dato es agregado, flow nos permite emitir dicho valor , el cual trae las actualizaciones en tiempo real.

Cuando el Viewmodel que esta haciendo el collect al repository muere, entonces se deja de escuchar los datos de firebase y se remueve la suscripcion mediante el codigo "awaitClose{suscription.remove()}"

[![flow](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124465454_398173844886884_7833435243146799708_n.png?_nc_cat=108&ccb=2&_nc_sid=ae9488&_nc_ohc=ivF2T8f6sJ0AX_Fao_i&_nc_ht=scontent.flim23-1.fna&oh=51df2f3b7db31451ed8da37a79a8e4d7&oe=5FD08B57 "flow")](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124465454_398173844886884_7833435243146799708_n.png?_nc_cat=108&ccb=2&_nc_sid=ae9488&_nc_ohc=ivF2T8f6sJ0AX_Fao_i&_nc_ht=scontent.flim23-1.fna&oh=51df2f3b7db31451ed8da37a79a8e4d7&oe=5FD08B57 "flow")


#### WorkManager

Worwkmanager, es un componente de la arquitectura de android , el cual forma parte de JetPack, workmanager nos permite ejecutar tareas en segundo plano siempre y cuando se cumplan ciertas restricciones.

En la aplicacion desarrollada , uno de los requerimientos es que se guarde la ubicacion del usuario cada 30 minutos. Workmanager nos permite realizar esa tarea en 2do plano, independientemente de si la aplicación se encuentea abierta, solo basta con cumplir con las restricciones establecidas para que dicha tarea se ejecute.

[![](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124182991_2767647296781145_8066543343677312223_n.png?_nc_cat=109&ccb=2&_nc_sid=ae9488&_nc_ohc=_6xR0YCyczMAX8kZ0rA&_nc_ht=scontent.flim23-1.fna&oh=04b24e394817bed84fe61e3fa9727975&oe=5FD0EF6C)](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124182991_2767647296781145_8066543343677312223_n.png?_nc_cat=109&ccb=2&_nc_sid=ae9488&_nc_ohc=_6xR0YCyczMAX8kZ0rA&_nc_ht=scontent.flim23-1.fna&oh=04b24e394817bed84fe61e3fa9727975&oe=5FD0EF6C)

En la siguiente captura se puede observar como se configura la restriccion la cual pide que el equipo este conectado a la red, para ejecutar la tarea , y se configura el lapso de tiempo en el cual se va ejecutar, que es cada 30 minutos

[![work](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124035931_375403823899326_833726350907740292_n.png?_nc_cat=109&ccb=2&_nc_sid=ae9488&_nc_ohc=-rq4fRc6YGgAX8M4vYc&_nc_ht=scontent.flim23-1.fna&oh=8664f7229323dc35a268cb3e9fb1260b&oe=5FD0A2DE "work")](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124035931_375403823899326_833726350907740292_n.png?_nc_cat=109&ccb=2&_nc_sid=ae9488&_nc_ohc=-rq4fRc6YGgAX8M4vYc&_nc_ht=scontent.flim23-1.fna&oh=8664f7229323dc35a268cb3e9fb1260b&oe=5FD0A2DE "work")

#### Navigation Component

El componente Navigation de Android Jetpack te permite implementar la navegación, desde simples clics de botones hasta patrones más complejos, como las barras de apps y los paneles laterales de navegación. El componente Navigation también garantiza una experiencia del usuario coherente y predecible, ya que se adhiere a un sistema establecido de conjunto de principios.

[![navi](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124573852_845436106226493_4344888886087520348_n.png?_nc_cat=102&ccb=2&_nc_sid=ae9488&_nc_ohc=MqcNLi1lzVwAX_ECXL8&_nc_ht=scontent.flim23-1.fna&oh=2ee1119b8785e23cf4816c40a7a54c5c&oe=5FD0B9F0 "navi")](https://scontent.flim23-1.fna.fbcdn.net/v/t1.15752-9/124573852_845436106226493_4344888886087520348_n.png?_nc_cat=102&ccb=2&_nc_sid=ae9488&_nc_ohc=MqcNLi1lzVwAX_ECXL8&_nc_ht=scontent.flim23-1.fna&oh=2ee1119b8785e23cf4816c40a7a54c5c&oe=5FD0B9F0 "navi")
