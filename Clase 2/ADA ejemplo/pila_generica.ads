generic
   type Elemento is private;
   Capacidad : Positive;

package Pila_Generica is
   procedure Apilar (Item : in Elemento);
   procedure Desapilar (Item : out Elemento);
   function Esta_Vacia return Boolean;
end Pila_Generica;

package body Pila_Generica is
   type Arreglo is array (1 .. Capacidad) of Elemento;
   Datos : Arreglo;
   Tope  : Integer := 0;

   procedure Apilar (Item : in Elemento) is
   begin
      if Tope < Capacidad then
         Tope := Tope + 1;
         Datos(Tope) := Item;
      end if;
   end Apilar;

   procedure Desapilar (Item : out Elemento) is
   begin
      if Tope > 0 then
         Item := Datos(Tope);
         Tope := Tope - 1;
      end if;
   end Desapilar;

   function Esta_Vacia return Boolean is
   begin
      return Tope = 0;
   end Esta_Vacia;

end Pila_Generica;
