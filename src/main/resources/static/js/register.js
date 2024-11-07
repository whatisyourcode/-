
    // 중복 확인을 위한 변수
    let isUserNameAvailable = false;
    let isUserIdAvailable = false;

    // 다음 버튼 활성화 함수
    function enableNextButton() {
    const nextButton = document.getElementById("nextButton");
    nextButton.disabled = !(isUserNameAvailable && isUserIdAvailable);
}

    // 각 단계로 이동하는 함수
    function goToNextStep(stepId) {
    document.querySelectorAll('.signup-card > form > div').forEach(div => div.style.display = 'none');
    document.getElementById(stepId).style.display = 'block';
}

    // 비밀번호 검증
    function validatePasswords() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const errorDiv = document.getElementById('password-error');

    if (password !== confirmPassword) {
    errorDiv.style.display = 'block';
} else {
    errorDiv.style.display = 'none';
    goToNextStep('email-step');
}
}

    // 아이디 검증
    document.getElementById("checkUserIdBtn").addEventListener("click", function() {
    var userId = document.querySelector('input[name="userId"]').value;
    fetch(`/user/register/userIdConfirm/${userId}`)
    .then(response => response.json())
    .then(isExist => {
    if (isExist) {
    alert("아이디가 이미 존재합니다.");
    isUserIdAvailable = false;
} else {
    alert("사용 가능한 아이디입니다.");
    isUserIdAvailable = true;
}
    enableNextButton();  // 버튼 상태 업데이트
})
    .catch(error => console.error("Error:", error));
});

    // 유저 이름 검증
    document.getElementById("checkUserNameBtn").addEventListener("click", function() {
    var userName = document.querySelector('input[name="userName"]').value;
    fetch(`/user/register/nameConfirm/${userName}`)
    .then(response => response.json())
    .then(isExist => {
    if (isExist) {
    alert("이름이 이미 존재합니다.");
    isUserNameAvailable = false;
} else {
    alert("사용 가능한 이름입니다.");
    isUserNameAvailable = true;
}
    enableNextButton();  // 버튼 상태 업데이트
})
    .catch(error => console.error("Error:", error));
});

