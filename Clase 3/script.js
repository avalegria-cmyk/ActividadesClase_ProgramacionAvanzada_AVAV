const THEMES = {
    correo: {
        from: '#2563eb',
        to: '#0ea5e9',
        soft: 'rgba(255, 255, 255, 0.18)',
        shadow: 'rgba(37, 99, 235, 0.28)',
        icon: `
            <svg stroke-linejoin="round" stroke-linecap="round" stroke-width="2" stroke="currentColor" fill="none" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" class="success-icon">
                <path d="M4 6h16v12H4z"></path>
                <path d="m4 7 8 6 8-6"></path>
            </svg>
        `
    },
    amigo: {
        from: '#16a34a',
        to: '#22c55e',
        soft: 'rgba(255, 255, 255, 0.18)',
        shadow: 'rgba(34, 197, 94, 0.28)',
        icon: `
            <svg stroke-linejoin="round" stroke-linecap="round" stroke-width="2" stroke="currentColor" fill="none" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" class="success-icon">
                <circle cx="9" cy="8" r="3"></circle>
                <path d="M4 19c0-2.2 2.2-4 5-4s5 1.8 5 4"></path>
                <path d="M18 8v6"></path>
                <path d="M15 11h6"></path>
            </svg>
        `
    },
    eliminar: {
        from: '#ef4444',
        to: '#f97316',
        soft: 'rgba(255, 255, 255, 0.18)',
        shadow: 'rgba(239, 68, 68, 0.28)',
        icon: `
            <svg stroke-linejoin="round" stroke-linecap="round" stroke-width="2" stroke="currentColor" fill="none" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" class="success-icon">
                <path d="M3 6h18"></path>
                <path d="M8 6V4h8v2"></path>
                <path d="m6 6 1 14h10l1-14"></path>
                <path d="M10 10v6"></path>
                <path d="M14 10v6"></path>
            </svg>
        `
    },
    general: {
        from: '#06b6d4',
        to: '#3b82f6',
        soft: 'rgba(255, 255, 255, 0.18)',
        shadow: 'rgba(59, 130, 246, 0.28)',
        icon: `
            <svg stroke-linejoin="round" stroke-linecap="round" stroke-width="2" stroke="currentColor" fill="none" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" class="success-icon">
                <path d="M9 12l2 2 4-4"></path>
                <circle r="10" cy="12" cx="12"></circle>
            </svg>
        `
    }
};

class BtnAccion extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ mode: 'open' });
    }

    connectedCallback() {
        const texto = this.getAttribute("texto") || "Botón";
        const mensaje = this.getAttribute("mensaje") || "Acción realizada";
        const descripcion = this.getAttribute("sub") || "Descripción";
        const tipo = this.getAttribute("tipo") || "general";
        const tema = THEMES[tipo] || THEMES.general;

        this.shadowRoot.innerHTML = `
        <style>
            :host {
                display: block;
                width: 100%;
                --from: ${tema.from};
                --to: ${tema.to};
                --shadow: ${tema.shadow};
            }

            button {
                width: 100%;
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 10px;
                padding: 14px 18px;
                border: 1px solid rgba(255, 255, 255, 0.12);
                border-radius: 14px;
                background: linear-gradient(135deg, var(--from), var(--to));
                color: white;
                cursor: pointer;
                font-size: 14px;
                font-weight: 600;
                letter-spacing: 0.3px;
                transition: transform 0.2s ease, box-shadow 0.25s ease, filter 0.25s ease;
                box-shadow: 0 10px 18px var(--shadow);
            }

            .btn-icon {
                display: inline-flex;
                align-items: center;
                justify-content: center;
                width: 20px;
                height: 20px;
            }

            .btn-icon svg {
                width: 18px;
                height: 18px;
            }

            button:hover {
                transform: translateY(-3px);
                box-shadow: 0 14px 24px var(--shadow);
                filter: brightness(1.05);
            }

            button:active {
                transform: scale(0.98);
            }
        </style>

        <button type="button">
            <span class="btn-icon">${tema.icon}</span>
            <span>${texto}</span>
        </button>
        `;

        const btn = this.shadowRoot.querySelector("button");

        btn.addEventListener("click", () => {
            const contenedor = document.getElementById("contenedor-notis");
            if (!contenedor) return;

            const noti = document.createElement("div");
            noti.className = "toast modern-success-message";
            noti.style.setProperty('--toast-from', tema.from);
            noti.style.setProperty('--toast-to', tema.to);
            noti.style.setProperty('--toast-soft', tema.soft);

            noti.innerHTML = `
            <button class="close-btn" aria-label="Cerrar notificación">×</button>
            <div class="icon-wrapper">
                ${tema.icon}
            </div>
            <div class="text-wrapper">
                <div class="title">${mensaje}</div>
                <div class="message">${descripcion}</div>
            </div>
            `;

            const cerrar = () => {
                if (!noti.isConnected) return;
                noti.classList.add("hide");
                setTimeout(() => noti.remove(), 250);
            };

            noti.querySelector(".close-btn").addEventListener("click", cerrar);
            contenedor.appendChild(noti);

            setTimeout(cerrar, 3200);
        });
    }
}

customElements.define("btn-accion", BtnAccion);
