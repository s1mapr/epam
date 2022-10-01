<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<title>Main Page</title>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
Hi ${sessionScope.user.login}!
</body>
</html>
