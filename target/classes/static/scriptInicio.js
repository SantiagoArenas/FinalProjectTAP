document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/medicos/me/salas', {
        headers: {
          'session': 'your-session-id' // replace with your actual session ID
        }
      })
      .then(response => response.json())
      .then(data => {
        const owlCarousel = document.querySelector('.owl-carousel');
        data.forEach(sala => {
          const salaElement = document.createElement('div');
          salaElement.className = 'item';
          salaElement.innerHTML = `
            <div class="card mb-4 shadow-sm">
              <div class="card-body text-center">
                <h5 class="card-title">${sala.nombreSala}</h5>
                <p class="card-text">Sala ${sala.numeroSala}</p>
                <p class="card-text">${sala.fecha}</p>
              </div>
            </div>
          `;
          owlCarousel.appendChild(salaElement);
        });
  
        // Inicializar OwlCarousel después de agregar los elementos
        $(document).ready(function() {
          $(".owl-carousel").owlCarousel({
            items: 1,
            loop: true,
            autoplay: true,
            autoplayTimeout: 5000, // 5 seconds
            nav: true,
            navText: ["<i class='fas fa-chevron-left'></i>", "<i class='fas fa-chevron-right'></i>"],
            dots: true,
            mouseDrag: true,
            touchDrag: true,
            pullDrag: true,
            animateOut: 'fadeOut',
            animateIn: 'fadeIn',
            responsive: {
              0: {
                items: 1,
                margin: 20
              },
              600: {
                items: 1,
                margin: 40
              },
              1000: {
                items: 1,
                margin: 60
              }
            }
          });
        });
      })
      .catch(error => console.error('Error:', error));
  });
  