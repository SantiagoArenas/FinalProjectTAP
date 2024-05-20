document.addEventListener('DOMContentLoaded', function() {
    const sessionId = 'your-session-id'; // replace with your actual session ID
    // Fetch medico info from the API
      fetch('/api/medicos/me', {
          method: 'GET',
          credentials: 'include'
      })
      .then(response => response.json())
      .then(data => {
          document.getElementById('nombre-perfil').textContent = data.nombre;

      })
      .catch(error => console.error('Error:', error));
});

document.getElementById('buscarForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const month = document.getElementById("mesSelect").value
    const year = document.getElementById("añoSelect").value

    console.log(month);
    console.log(year);

    fetch(`/api/medicos/me/calendario`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({'año': year, 'mes': month})
            })
            .then(response => response.json())
            .catch(error => {
                console.error('Error:', error);
            });

    setTimeout(mostrar, 3000);

    function mostrar(){
        fetch(`/api/medicos/me/calendario/${year}/${month}`, {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    })
                    .then(response => response.json())
                    .then(data => {
                        console.log(data);
                        flashcards = document.getElementById("flashcards");
                        data.forEach(dia => {
                            const fecha = dia.dia;
                            const medico = dia.medico;
                            const sala = dia.sala;
                            const turno = dia.turno;
                            if (fecha != null && medico != null && sala != null && turno != null){
                                const card = document.createElement('div');
                                          card.className = 'item';
                                          card.innerHTML = `
                                            <div class="card mb-4 shadow-sm">
                                              <div class="card-body text-center">
                                                <h5 class="card-title">${fecha.substring(0, 10)}</h5>
                                                <p class="card-text">Sala: ${sala.nombre}</p>
                                                <p class="card-text">Turno: ${turno}</p>
                                                <p class="card-text">${medico.nombre}</p>
                                              </div>
                                            </div>
                                          `;
                                flashcards.appendChild(card);
                            }
                        });
                            })
                    .catch(error => {
                        console.error('Error:', error);
                    });
    }

});