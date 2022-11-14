//Таблица всех пользователей
const url = 'http://localhost:8080/api/admin/users/';
const renderTable = document.getElementById("allUsersTable");
const addForm = document.getElementById("add-form");

const renderPosts = (users) => {
    let temp = '';
    users.forEach((u) => {
        temp += `<tr>
                                <td>${u.id}</td>
                                <td id=${'name' + u.id}>${u.username}</td>
                                <td id=${'age' + u.id}>${u.age}</td>
                                <td id=${'email' + u.id}>${u.email}</td>
                                <td id=${'role' + u.id}>${u.role}</td>
                                <td>
                                <button class="btn btn-info" type="button"
                                data-toggle="modal" data-target="#modalEdit"
                                onclick="editModal(${u.id})">Edit</button></td>
                                <td>
                                <button class="btn btn-danger" type="button"
                                data-toggle="modal" data-target="#modalDelete"
                                onclick="deleteModal(${u.id})">Delete</button></td>
                                </tr>
                                `
    })
    renderTable.innerHTML = temp;
}

function getAllUsers() {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            renderPosts(data)
        })
}

getAllUsers()

// Добавление пользователя

addForm.addEventListener('submit', (e) => {
    e.preventDefault();
    let nameValue = document.getElementById("username").value;
    let ageValue = document.getElementById("age").value;
    let emailValue = document.getElementById("email").value;
    let passwordValue = document.getElementById("password").value;
    let roles = getRoles(Array.from(document.getElementById("addRoles").selectedOptions).map(role => role.value));
    let newUser = {
        username: nameValue,
        age: ageValue,
        email: emailValue,
        password: passwordValue,
        roles: roles
    }
    fetch(url, {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(newUser)
    })
        .then(data => {
        const dataArr = [];
        dataArr.push(data);
        getAllUsers(data);
    }).then(() => {
            document.getElementById("nav-home-tab").click();})
})


// addForm.addEventListener('submit', addUser)
// function addUser() {
//     let nameValue = document.getElementById("username").value;
//     let ageValue = document.getElementById("age").value;
//     let emailValue = document.getElementById("email").value;
//     let passwordValue = document.getElementById("password").value;
//     let roles = getRoles(Array.from(document.getElementById("addRoles").selectedOptions).map(role => role.value));
//
//     let newUser = {
//         username: nameValue,
//         age: ageValue,
//         email: emailValue,
//         password: passwordValue,
//         roles: roles
//     }
//
//     fetch(url, {
//         method: "POST",
//         headers: {
//             'Accept': 'application/json',
//             'Content-Type': 'application/json;charset=UTF-8'
//         },
//         body: JSON.stringify(newUser)
//     })
//         .then(() => {
//             document.getElementById("nav-home-tab").click();
//             getAllUsers();
//         })
// }


function getRoles(rols) {
    let roles = [];
    if (rols.indexOf("ADMIN") >= 0) {
        roles.push({"id": 1});
    }
    if (rols.indexOf("USER") >= 0) {
        roles.push({"id": 2});
    }
    return roles;
}

// Delete
function deleteModal(id) {
    fetch(url + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(us => {
            document.getElementById('idDelete').value = us.id;
            document.getElementById('usernameDelete').value = us.username;
            document.getElementById('ageDelete').value = us.age;
            document.getElementById('emailDelete').value = us.email;
        })
    });
}

async function deleteUser() {
    console.log(document.getElementById('idDelete').value)
    await fetch(url + document.getElementById('idDelete').value, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(document.getElementById('idDelete').value)
    })

    getAllUsers()
    document.getElementById("deleteButton").click();
}

// Edit
function editModal(id) {
    fetch(url + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(us => {

            document.getElementById('idEdit').value = us.id;
            document.getElementById('usernameEdit').value = us.username;
            document.getElementById('ageEdit').value = us.age;
            document.getElementById('emailEdit').value = us.email;
            document.getElementById('passwordEdit').value = us.password;

        })
    });
}

async function editUser() {
    let idValue = document.getElementById("idEdit").value;
    let nameValue = document.getElementById("usernameEdit").value;
    let ageValue = document.getElementById("ageEdit").value;
    let emailValue = document.getElementById("emailEdit").value;
    let passwordValue = document.getElementById("passwordEdit").value;
    let roles = getRoles(Array.from(document.getElementById("editRoles").selectedOptions).map(role => role.value));

    let user = {
        id: idValue,
        username: nameValue,
        age: ageValue,
        email: emailValue,
        password: passwordValue,
        roles: roles
    }

    await fetch(url, {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });
    getAllUsers()
    document.getElementById("updateButton").click();
}

//User
const tableForUser = document.getElementById("tableForUser");
const urlAuth = 'http://localhost:8080/api/admin/authentication';
const panel = document.getElementById("admin-panel");

function userAuthInfo() {
    fetch(urlAuth)
        .then((res) => res.json())
        .then((u) => {
            let temp = '';
            temp += `<tr>
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.age}</td>
            <td>${u.email}</td>
            <td>${u.role}</td>
            </tr>`;
            tableForUser.innerHTML = temp;
        });
}

userAuthInfo()

function userPanel() {
    fetch(urlAuth)
        .then((res) => res.json())
        .then((u) => {
            panel.innerHTML = `<h5>${u.email} with roles: ${u.role}</h5>`
        });
}

userPanel()
