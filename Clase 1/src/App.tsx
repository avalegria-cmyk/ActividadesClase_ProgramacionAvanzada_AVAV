
import { ModoOscuro } from './components/ModoOscuro'
import './App.css'

function App() {

  return (
    <div style={{ display: 'flex', gap: '20px', padding: '20px', flexWrap: 'wrap' }}>
      {/* Componente 1 */}
      <div style={{ flex: 1, minWidth: '300px' }}>
        <ModoOscuro
          titulo="Tema de la Aplicación"
          descripcion="Controla el tema claro/oscuro de toda la app"
          emojiInicial="☀️"
          emojiFinal="🌙"
          nombreEstadoOff="Tema Claro"
          nombreEstadoOn="Tema Oscuro"
        />
      </div>

      {/*  Componente 2  */}
      <div style={{ flex: 1, minWidth: '300px' }}>
        <ModoOscuro
          titulo="Modo Lectura"
          descripcion="Ajusta el tamaño del texto y el contraste"
          emojiInicial="👓"
          emojiFinal="📖"
          nombreEstadoOff="Lectura Normal"
          nombreEstadoOn="Lectura Mejorada"
        />
      </div>
      
      
    </div>
  )
}

export default App
