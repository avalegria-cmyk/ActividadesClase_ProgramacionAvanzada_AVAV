class Counter extends HTMLElement {
    constructor() {
        super();
        this.count = 0;

        this.attachShadow({ mode: 'open' });
        this.shadowRoot.innerHTML = `
        <style>
            button {
                font-size: 2rem;
                padding: 1rem 2rem;
                cursor: pointer;
                background-color: #0a2542;
                color: white;
                border: none;
                border-radius: 5px;
            }
        </style>
        <button id="btn">Contador: 0</button>
        `;
    }

    connectedCallback() {
        const btn = this.shadowRoot.getElementById('btn');

        btn.addEventListener('click', () => {
            this.count++;
            btn.textContent = 'Contador: ' + this.count; 
        });
    }
}

customElements.define('btn-counter', Counter);
