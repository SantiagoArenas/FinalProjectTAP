document.getElementById('isBoss').addEventListener('change', function() {
    document.getElementById('bossInfo').style.display = this.checked ? 'block' : 'none';
});

document.getElementById('numConocimientos').addEventListener('change', function() {
    var conocimientosInfo = document.getElementById('conocimientosInfo');
    conocimientosInfo.innerHTML = '';
    for (var i = 0; i < this.value; i++) {
        var conocimientoDiv = document.createElement('div');
        conocimientoDiv.className = 'conocimiento';

        var nombreConocimiento = document.createElement('input');
        nombreConocimiento.type = 'text';
        nombreConocimiento.name = 'nombreConocimiento' + i;
        nombreConocimiento.id = 'nombreConocimiento' + i;
        nombreConocimiento.placeholder = 'Tipo de técnica';
        conocimientoDiv.appendChild(nombreConocimiento);

        conocimientosInfo.appendChild(conocimientoDiv);
    }
});

document.getElementById('numRooms').addEventListener('change', function() {
    var roomsInfo = document.getElementById('roomsInfo');
    roomsInfo.innerHTML = '';
    for (var i = 0; i < this.value; i++) {
        var roomDiv = document.createElement('div');
        roomDiv.className = 'room';

        var roomName = document.createElement('input');
        roomName.type = 'text';
        roomName.name = 'roomName' + i;
        roomName.id = 'roomName' + i;
        roomName.placeholder = 'Nombre de la sala';
        roomDiv.appendChild(roomName);

        var roomPriority = document.createElement('input');
        roomPriority.type = 'number';
        roomPriority.name = 'roomPriority' + i;
        roomPriority.id = 'roomPriority' + i;
        roomPriority.placeholder = 'Prioridad de la sala';
        roomDiv.appendChild(roomPriority);

        roomsInfo.appendChild(roomDiv);
    }
});

document.getElementById('numDoctors').addEventListener('change', function() {
    var doctorsInfo = document.getElementById('doctorsInfo');
    doctorsInfo.innerHTML = '';
    for (var i = 0; i < this.value; i++) {
        var doctorDiv = document.createElement('div');
        doctorDiv.className = 'doctor';

        var doctorName = document.createElement('input');
        doctorName.type = 'text';
        doctorName.name = 'doctorName' + i;
        doctorName.id = 'doctorName' + i;
        doctorName.placeholder = 'Nombre del médico';
        doctorDiv.appendChild(doctorName);

        var doctorEmail = document.createElement('input');
        doctorEmail.type = 'email';
        doctorEmail.name = 'doctorEmail' + i;
        doctorEmail.id = 'doctorEmail' + i;
        doctorEmail.placeholder = 'Email del médico';
        doctorDiv.appendChild(doctorEmail);

        var doctorPassword = document.createElement('input');
        doctorPassword.type = 'password';
        doctorPassword.name = 'doctorPassword' + i;
        doctorPassword.id = 'doctorPassword' + i;
        doctorPassword.placeholder = 'Contraseña del médico';
        doctorDiv.appendChild(doctorPassword);

        doctorsInfo.appendChild(doctorDiv);
    }
});

document.getElementById('registroForm').addEventListener('submit', function(e) {
    e.preventDefault();

    var jefe = {
        nombre: this['username'].value,
        email: this['email'].value,
        password: this['password'].value,
        jefe: this['isBoss'].checked
    };

    fetch('/api/medicos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(jefe),
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);

            var doctors = [];
            for (var i = 0; i < this.numDoctors.value; i++) {
                var doctor = {
                    nombre: this['doctorName' + i].value,
                    email: this['doctorEmail' + i].value,
                    password: this['doctorPassword' + i].value,
                    jefe: false
                };
                doctors.push(doctor);
            }

            var rooms = [];
            for (var i = 0; i < this.numRooms.value; i++) {
                var room = {
                    nombre: this['roomName' + i].value,
                    prioridad: this['roomPriority' + i].value
                };
                rooms.push(room);
            }

            var fetches = [];

            doctors.forEach(doctor => {
                fetches.push(fetch('/api/medicos', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(doctor),
                }));
            });

            rooms.forEach(room => {
                fetches.push(fetch('api/medicos/me/salas', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(room),
                }));
            });

            return Promise.all(fetches);
        })
        .then(responses => {
            responses.forEach(response => {
                if (!response.ok) {
                    throw new Error('Error en el fetch');
                }
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
    window.location.href = 'inicio.html';
});