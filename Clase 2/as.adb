-- Especificación del componente (interfaz pública)
package Sensor_Temperatura is
   type Lectura is range -50 .. 150;
   
   function Obtener_Temperatura return Lectura;
   procedure Calibrar (Offset : in Integer);
   
private
   -- Implementación oculta al exterior
   Estado_Calibrado : Boolean := False;
end Sensor_Temperatura;

-- Cuerpo del componente (implementación)
package body Sensor_Temperatura is
   Calibracion_Actual : Integer := 0;

   function Obtener_Temperatura return Lectura is
   begin
      return Lectura(Hardware.Leer_ADC + Calibracion_Actual);
   end;

   procedure Calibrar (Offset : in Integer) is
   begin
      Calibracion_Actual := Offset;
      Estado_Calibrado := True;
   end;
end Sensor_Temperatura;
    

-- Un componente "pila" reutilizable para CUALQUIER tipo de dato
generic
   type Elemento is private;
   Capacidad : Positive;
package Pila_Generica is
   procedure Apilar (Item : in Elemento);
   procedure Desapilar (Item : out Elemento);
   function Esta_Vacia return Boolean;
end Pila_Generica;

-- Instanciación: ahora tienes una pila de enteros
package Pila_Enteros is new Pila_Generica(Elemento => Integer, Capacidad => 100);

-- Y una pila de caracteres, mismo componente
package Pila_Chars is new Pila_Generica(Elemento => Character, Capacidad => 50);


-- El componente "promete" condiciones de entrada y salida
function Dividir (A, B : Float) return Float
   with Pre  => B /= 0.0,           -- Precondición: no dividir por cero
        Post => abs(Dividir'Result) >= 0.0;  -- Postcondición verificable

