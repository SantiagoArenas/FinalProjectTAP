document.addEventListener("DOMContentLoaded", function() {
    // Funcionalidad de inicio de sesión
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            fetch('API_LOGIN_ENDPOINT', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, password })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        localStorage.setItem('token', data.token);
                        window.location.href = 'index.html';
                    } else {
                        alert('Login fallido');
                    }
                });
        });
    }

    // Funcionalidad de creación de departamento
    const departmentForm = document.getElementById('departmentForm');
    if (departmentForm) {
        departmentForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const departmentName = document.getElementById('departmentName').value;
            const token = localStorage.getItem('token');

            fetch('API_DEPARTMENT_ENDPOINT', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({ name: departmentName })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert('Departamento creado');
                    } else {
                        alert('Error al crear el departamento');
                    }
                });
        });
    }

    // Funcionalidad de perfil de usuario
    const editProfileBtn = document.getElementById('editProfileBtn');
    const logoutBtn = document.getElementById('logoutBtn');

    if (editProfileBtn && logoutBtn) {
        const token = localStorage.getItem('token');

        // Obtener datos del usuario
        fetch('API_USER_ENDPOINT', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    document.getElementById('userName').innerText = data.user.name;
                    document.getElementById('userEmail').innerText = `Email: ${data.user.email}`;
                }
            });

        // Editar perfil de usuario
        editProfileBtn.addEventListener('click', function() {
            // Lógica para editar perfil
        });

        // Cerrar sesión
        logoutBtn.addEventListener('click', function() {
            localStorage.removeItem('token');
            window.location.href = 'login.html';
        });
    }

    // Modo oscuro
    const toggleDarkMode = document.getElementById('toggleDarkMode');
    if (toggleDarkMode) {
        toggleDarkMode.addEventListener('change', function() {
            if (this.checked) {
                document.body.classList.add('dark-mode');
                localStorage.setItem('darkMode', 'enabled');
            } else {
                document.body.classList.remove('dark-mode');
                localStorage.setItem('darkMode', 'disabled');
            }
        });

        // Aplicar preferencia de modo oscuro
        if (localStorage.getItem('darkMode') === 'enabled') {
            document.body.classList.add('dark-mode');
            toggleDarkMode.checked = true;
        }
    }
});
