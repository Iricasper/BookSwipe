# BookSwipe
## Descripción de la aplicación
Aplicación basada en Kotlin, diseñada para Android con base de datos en Firebase
### LoginScreen
Formulario básico con usuario y contraseña,
validación de formato de correo.
Botón para cambiar a la pantalla de registro
### RegisterScreen
Formulario para registro en el **Auth de Firebase** y en la tabla **Usuario** de la **RealtimeDatabase**
Validación de datos:
- Correo con formato válido
- Correo no repetido
- Teléfono con 9 caracteres
- Contraseña superior o igual a 6 caracteres
- Contraseña insegura (no configurado)
- Validación de la contraseña al repetirla (deben coincidir)
Botón para cambiar a la pantalla de login
Al crear una cuenta de forma exitosa se redirigirá automáticamente al login
### HomeScreen
Muestra el correo de usuario del usuario activo y un interruptor para elegir si mantener la sesión iniciada.
También hay un botón para cerrar sesión y desmarcar automáticamente el mantener sesión.
Vuelve a la pantalla de login
## Guía de uso
1. Descomprimir el zip.
2. Descargar el `google-services.json` desde el proyecto de **Firebase**
3. Agregar las dependencias de google:
A nivel de proyecto `BookSwipe/build.gradle.kts`:
```
plugins {
  id("com.android.application") version "7.3.0" apply false
  // ...

  // Add the dependency for the Google services Gradle plugin
  id("com.google.gms.google-services") version "4.4.4" apply false
}
```
A nivel de app `BookSwipe/src/build.gradle.kts`:
```
plugins {
  id("com.android.application")

  // Add the Google services Gradle plugin
  id("com.google.gms.google-services")
  // ...
}
```
3. Construir el proyecto (Build)
4. Lanzar la aplicación mediante el emulador de **Android Studio** o en un dispositivo *Android*
5. Probar el login con el usuario `acasper631@g.educaand.es` y la contraseña `123456`
