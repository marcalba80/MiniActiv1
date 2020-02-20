# MiniActiv1 - Ús de sensors
A) Explorar el maneig de l’acceleròmetre

1. Comproveu quines bones pràctiques han estat aplicades.

    • No es verifica si disposem del sensor, en aquest cas d'acceleròmetre. No s’aplica la primera bona pràctica.
    
    • Es registra i s’allibera el sensor correctament, al onCreate() i al onPause(). Segona bona pràctica.
    
    • Quan es detecta un canvi al sensor s’actualitzen unes variables i es canvia el color d’un textView, no s’aturan l’aplicació. A més, es tria el DELAY_NORMAL ajustant-se a les necessitats d’ús del sensor. Tercera i quarta bona pràctica.
    
    • No s’usa cap mètode o constant DEPRECATED. Cinquena bona pràctica.
    
    • Es prova l’aplicació amb un emulador d’Android. Sisena bona pràctica.
    
    • El listener s’allibera al onPause(), en sistemes android amb la API >= 28 no es reben els events generats pel sensor i no es tractaran les dades. Per tant, es recomanable alliberar-lo al onPause(). Setena bona pràctica.
    
    • No s’indica al fitxer manifest l’ús de l'acceleròmetre. No s’aplica la vuitena bona pràctica.
    
2. El sensor deixa de funcionar, quan passa a estar en segon pla l’activitat i es torna a activar. Cal tornar a registrar el listener al onResume().
