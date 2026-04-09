with Ada.Text_IO; use Ada.Text_IO;
with Sensor_Temperatura;
with Pila_Generica;

procedure Main is

   -- Crear pila de enteros
   package Pila_Enteros is new Pila_Generica(Integer, 10);

   Temp : Sensor_Temperatura.Lectura;
   Valor : Integer;

begin
   Put_Line("=== SENSOR ===");

   Temp := Sensor_Temperatura.Obtener_Temperatura;
   Put_Line("Temperatura: " & Integer'Image(Integer(Temp)));

   Sensor_Temperatura.Calibrar(5);

   Temp := Sensor_Temperatura.Obtener_Temperatura;
   Put_Line("Temperatura calibrada: " & Integer'Image(Integer(Temp)));

   Put_Line("=== PILA ===");

   Pila_Enteros.Apilar(10);
   Pila_Enteros.Apilar(20);

   Pila_Enteros.Desapilar(Valor);
   Put_Line("Elemento sacado: " & Integer'Image(Valor));

end Main;
