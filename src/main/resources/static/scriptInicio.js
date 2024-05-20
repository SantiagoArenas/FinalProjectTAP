  // Show modal to crear conocimiento
  document.getElementById('crear-conocimiento-btn').addEventListener('click', function() {
      $('#crearConocimientoModal').modal('show');
  });

  // Handle añadir conocimiento form submission
  document.getElementById('crearConocimientoForm').addEventListener('submit', function(event) {
      event.preventDefault();
      const conocimiento = document.getElementById('conocimiento').value;

      fetch('/api/conocimientos', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          credentials: 'include', // Important for including cookies in the request
          body: JSON.stringify({'nombre': conocimiento})
      })
      .then(response => response.json())
      .then(data => {
          $('#crearConocimientoModal').modal('hide');
          window.location.reload();
      })
      .catch(error => {
          console.error('Error:', error);
      });
  });