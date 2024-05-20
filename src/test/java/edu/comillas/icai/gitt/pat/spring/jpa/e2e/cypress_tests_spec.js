describe('Pruebas exhaustivas del proyecto', () => {

  // Prueba para la página principal
  it('Visita la página principal', () => {
    cy.visit('http://localhost:8080')
    cy.contains('h1', 'Bienvenido')
  })

  // Prueba CRUD para Calendario
  it('CRUD de Calendario', () => {
    // Crear un nuevo Calendario
    cy.request('POST', 'http://localhost:8080/api/calendario', {
      fecha: '2024-05-20',
      evento: 'Evento de prueba'
    }).then((response) => {
      expect(response.status).to.eq(201)
      const calendarioId = response.body.id

      // Leer el Calendario
      cy.request('GET', `http://localhost:8080/api/calendario/${calendarioId}`).then((response) => {
        expect(response.status).to.eq(200)
        expect(response.body.evento).to.eq('Evento de prueba')

        // Actualizar el Calendario
        cy.request('PUT', `http://localhost:8080/api/calendario/${calendarioId}`, {
          fecha: '2024-05-20',
          evento: 'Evento actualizado'
        }).then((response) => {
          expect(response.status).to.eq(200)

          // Verificar actualización
          cy.request('GET', `http://localhost:8080/api/calendario/${calendarioId}`).then((response) => {
            expect(response.status).to.eq(200)
            expect(response.body.evento).to.eq('Evento actualizado')

            // Borrar el Calendario
            cy.request('DELETE', `http://localhost:8080/api/calendario/${calendarioId}`).then((response) => {
              expect(response.status).to.eq(204)
            })
          })
        })
      })
    })
  })

  it('CRUD de Medico', () => {
    // Crear un nuevo Medico
    cy.request('POST', 'http://localhost:8080/api/medico', {
      nombre: 'Dr. Prueba',
      especialidad: 'Cardiología'
    }).then((response) => {
      expect(response.status).to.eq(201)
      const medicoId = response.body.id

      // Leer el Medico
      cy.request('GET', `http://localhost:8080/api/medico/${medicoId}`).then((response) => {
        expect(response.status).to.eq(200)
        expect(response.body.nombre).to.eq('Dr. Prueba')

        // Actualizar el Medico
        cy.request('PUT', `http://localhost:8080/api/medico/${medicoId}`, {
          nombre: 'Dr. Actualizado',
          especialidad: 'Cardiología'
        }).then((response) => {
          expect(response.status).to.eq(200)

          // Verificar actualización
          cy.request('GET', `http://localhost:8080/api/medico/${medicoId}`).then((response) => {
            expect(response.status).to.eq(200)
            expect(response.body.nombre).to.eq('Dr. Actualizado')

            // Borrar el Medico
            cy.request('DELETE', `http://localhost:8080/api/medico/${medicoId}`).then((response) => {
              expect(response.status).to.eq(204)
            })
          })
        })
      })
    })
  })

  // Prueba CRUD para Sala
  it('CRUD de Sala', () => {
    // Crear una nueva Sala
    cy.request('POST', 'http://localhost:8080/api/sala', {
      nombre: 'Sala de Prueba',
      ubicacion: 'Edificio A'
    }).then((response) => {
      expect(response.status).to.eq(201)
      const salaId = response.body.id

      // Leer la Sala
      cy.request('GET', `http://localhost:8080/api/sala/${salaId}`).then((response) => {
        expect(response.status).to.eq(200)
        expect(response.body.nombre).to.eq('Sala de Prueba')

        // Actualizar la Sala
        cy.request('PUT', `http://localhost:8080/api/sala/${salaId}`, {
          nombre: 'Sala Actualizada',
          ubicacion: 'Edificio B'
        }).then((response) => {
          expect(response.status).to.eq(200)

          // Verificar actualización
          cy.request('GET', `http://localhost:8080/api/sala/${salaId}`).then((response) => {
            expect(response.status).to.eq(200)
            expect(response.body.nombre).to.eq('Sala Actualizada')

            // Borrar la Sala
            cy.request('DELETE', `http://localhost:8080/api/sala/${salaId}`).then((response) => {
              expect(response.status).to.eq(204)
            })
          })
        })
      })
    })
  })

  it('CRUD de Conocimiento', () => {
    // Crear un nuevo Conocimiento
    cy.request('POST', 'http://localhost:8080/api/conocimiento', {
      titulo: 'Conocimiento de Prueba',
      descripcion: 'Descripción de prueba'
    }).then((response) => {
      expect(response.status).to.eq(201)
      const conocimientoId = response.body.id

      // Leer el Conocimiento
      cy.request('GET', `http://localhost:8080/api/conocimiento/${conocimientoId}`).then((response) => {
        expect(response.status).to.eq(200)
        expect(response.body.titulo).to.eq('Conocimiento de Prueba')

        // Actualizar el Conocimiento
        cy.request('PUT', `http://localhost:8080/api/conocimiento/${conocimientoId}`, {
          titulo: 'Conocimiento Actualizado',
          descripcion: 'Descripción actualizada'
        }).then((response) => {
          expect(response.status).to.eq(200)

          // Verificar actualización
          cy.request('GET', `http://localhost:8080/api/conocimiento/${conocimientoId}`).then((response) => {
            expect(response.status).to.eq(200)
            expect(response.body.titulo).to.eq('Conocimiento Actualizado')

            // Borrar el Conocimiento
            cy.request('DELETE', `http://localhost:8080/api/conocimiento/${conocimientoId}`).then((response) => {
              expect(response.status).to.eq(204)
            })
          })
        })
      })
    })
  })
})
