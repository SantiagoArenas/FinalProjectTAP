document.addEventListener('DOMContentLoaded', function() {
    const sessionId = 'your-session-id'; // replace with your actual session ID

    // Fetch salas and update the select element
    fetch('/api/medicos/me/salas', {
        headers: {
            'session': sessionId
        }
    })
    .then(response => response.json())
    .then(data => {
        const salaSelect = document.getElementById('salaSelect');
        data.forEach(sala => {
            const option = document.createElement('option');
            option.value = sala.nombreSala;
            option.textContent = sala.nombreSala;
            salaSelect.appendChild(option);
        });
    })
    .catch(error => console.error('Error fetching salas:', error));

    // Fetch medicos and update the select element
    fetch('/api/medicos', {
        headers: {
            'session': sessionId
        }
    })
    .then(response => response.json())
    .then(data => {
        const medicoSelect = document.getElementById('medicoSelect');
        console.log(data);
        data.forEach(medico => {
            const option = document.createElement('option');
            option.value = medico.id; // assuming 'id' is the identifier for medico
            option.textContent = medico.nombre; // assuming 'nombre' is the name of medico
            medicoSelect.appendChild(option);
        });
    })
    .catch(error => console.error('Error fetching medicos:', error));
});
