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
        });

        window.location.href = 'inicio.html';
});

