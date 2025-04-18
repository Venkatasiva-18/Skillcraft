function validateForm(event) {
    event.preventDefault(); // Prevent form submission

    var name = document.getElementById("name").value.trim();
    var email = document.getElementById("email").value.trim();
    var mobile = document.getElementById("mobile").value.trim();
    var message = document.getElementById("message").value.trim();

    // Regex for email validation
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    // Regex for mobile number validation (example: 10 digits)
    var mobilePattern = /^[0-9]{10}$/;

    // Name validation
    if (name === "") {
        alert("Name is required.");
        return;
    }

    // Email validation
    if (!emailPattern.test(email)) {
        alert("Please enter a valid email address.");
        return;
    }

    // Mobile number validation
    if (!mobilePattern.test(mobile)) {
        alert("Please enter a valid 10-digit mobile number.");
        return;
    }

    // Message validation
    if (message === "") {
        alert("Message is required.");
        return;
    }

    // Proceed to send email if all validations pass
    sendMail(name, email, mobile, message);
}

function sendMail(name, email, mobile, message) {
    var params = {
        name: name,
        email: email,
        mobile: mobile,
        message: message,
    };

    emailjs
        .send("service_77qz6io", "template_7f2gbis", params)
        .then(function (res) {
            alert("sent successfully!");
            // Optionally, clear the form fields after sending
            //document.getElementById("contactForm").reset();
        })
        .catch(function (error) {
            alert("Failed to send email. Please try again.");
            console.error(error);
        });
}
