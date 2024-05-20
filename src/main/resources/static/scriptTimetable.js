document.addEventListener('DOMContentLoaded', function() {
    const sessionId = 'your-session-id'; // replace with your actual session ID

    // Fetch salas and update the select element
    fetch('/api/medicos/me/salas', {
        method: 'GET',
        credentials: 'include' // Important for including cookies in the request
    })
        .then(response => response.json())
        .then(data => {
            const salaSelect = document.getElementById('salaSelect');
            data.forEach(sala => {
                const option = document.createElement('option');
                option.value = sala.id;
                option.textContent = sala.nombre;
                salaSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching salas:', error));

    // Fetch medicos and update the select element
    fetch('/api/medicos', {
        method: 'GET',
        credentials: 'include' // Important for including cookies in the request
    })
        .then(response => response.json())
        .then(data => {
            const medicoSelect = document.getElementById('medicoSelect');
            data.forEach(medico => {
                const option = document.createElement('option');
                option.value = medico.id; // assuming 'id' is the identifier for medico
                option.textContent = medico.nombre; // assuming 'nombre' is the name of medico
                medicoSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching medicos:', error));

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
