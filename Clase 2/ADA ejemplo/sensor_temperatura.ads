package Sensor_Temperatura is
   type Lectura is range -50 .. 150;

   function Obtener_Temperatura return Lectura;
   procedure Calibrar (Offset : in Integer);
end Sensor_Temperatura;

package body Sensor_Temperatura is
   Calibracion_Actual : Integer := 0;

   function Obtener_Temperatura return Lectura is
      Valor_Base : Integer := 25; -- simulamos temperatura fija
   begin
      return Lectura(Valor_Base + Calibracion_Actual);
   end Obtener_Temperatura;

   procedure Calibrar (Offset : in Integer) is
   begin
      Calibracion_Actual := Offset;
   end Calibrar;

end Sensor_Temperatura;
