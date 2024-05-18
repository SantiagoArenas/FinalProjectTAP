fetch('API_URL/USER_ID')
    .then(response => response.json())
    .then(data => {
        document.getElementById('userImage').src = data.image;
        document.getElementById('userName').textContent = data.name;
        document.getElementById('userBio').textContent = data.bio;
    })
    .catch(error => console.error('Error:', error));

function createCard(day) {
    var card = '<div class="col-md-4"><div class="card mb-4 shadow-sm"><div class="card-body text-center">';
    if (day.length > 0) {
        // Aquí puedes agregar el contenido de la tarjeta basado en los datos del día
        card += '<h5 class="card-title">' + day[0].title + '</h5>';
        card += '<p class="card-text">' + day[0].text + '</p>';
    } else {
        // Si no hay datos para el día, agrega una tarjeta vacía
        card += '<h5 class="card-title">No hay eventos para este día</h5>';
    }
    card += '</div></div></div>';
    return card;
}

function fillRows(data) {
    for (var day in data) {
        var row = document.getElementById(day);
        for (var i = 0; i < data[day].length; i++) {
            row.innerHTML += createCard(data[day][i]);
        }
        // Si no hay datos para el sábado o el domingo, agrega una tarjeta vacía
        if ((day === 'saturday' || day === 'sunday') && data[day].length === 0) {
            row.innerHTML += createCard([]);
        }
    }
}

fillRows(data);