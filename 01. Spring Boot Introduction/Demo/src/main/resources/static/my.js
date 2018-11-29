let btn = document.getElementById("submit-btn");
btn.onclick = e => {
    fetch("/testJsp/pesho", {
        method: "POST",
        data: {
            username: "pesho",
            password: "123",
            confirm: "123"
        }
    }).then(dto => {
        document.write("<h1>" + dto.username + "</h1>");
    })
};