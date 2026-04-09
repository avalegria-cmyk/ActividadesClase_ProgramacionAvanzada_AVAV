import { useState } from 'react';
import './ModoOscuro.css';

/*CONTRATO*/
interface Props {
  titulo: string;
  descripcion: string;
  emojiInicial?: string;
  emojiFinal?: string;
  nombreEstadoOff?: string;
  nombreEstadoOn?: string;
}

export function ModoOscuro({
  titulo,
  descripcion,
  emojiInicial = '🌙',
  emojiFinal = '☀️',
  nombreEstadoOff = 'Modo Claro',
  nombreEstadoOn = 'Modo Oscuro',
}: Props) {
  const [temaOscuro, setTemaOscuro] = useState(false);// Estado

  const cambiarTema = () => {
    setTemaOscuro(!temaOscuro);//true/false
  };

  const claseTema = temaOscuro ? 'tema-oscuro' : 'tema-claro';
  const emoji = temaOscuro ? emojiFinal : emojiInicial;
  const nombreTema = temaOscuro ? nombreEstadoOn : nombreEstadoOff;

  return (
    <div className={claseTema}>
      <div className="contenedor">
        <h1>{titulo}</h1>

        <p>{descripcion}</p>

        <button
          className="btn-tema"
          onClick={cambiarTema}
          title={`Cambiar a ${temaOscuro ? nombreEstadoOff.toLowerCase() : nombreEstadoOn.toLowerCase()}`}
        >
          {emoji}
        </button>

        <div className="info-tema">Estás en: {nombreTema}</div>
      </div>
    </div>
  );
}
