# Spring Boot with AWS ECR and Docker Compose

## Overview

Deploy a Spring Boot application using a Docker image stored in Amazon ECR, together with MySQL and Redis using Docker Compose.

## Tech Stack

- Java 21
- Spring Boot
- Docker / Docker Compose
- AWS IAM
- Amazon ECR
- AWS CLI
- MySQL 8
- Redis 7

## 1. AWS ECR Setup

**AWS Region:**
```text
ap-southeast-2
```

**IAM User:**
```text
ecr-local-user
```

**ECR Repository:**
```text
springboot-app-ecr-lab
```

**Repository URI:**
```text
403351349592.dkr.ecr.ap-southeast-2.amazonaws.com/springboot-app-ecr-lab
```

## 2. IAM User Policy

Created an IAM user named `ecr-local-user` and attached the following custom policy.

**Policy JSON:**

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "ECRAuthentication",
      "Effect": "Allow",
      "Action": [
        "ecr:GetAuthorizationToken"
      ],
      "Resource": "*"
    },
    {
      "Sid": "ECRRepositoryAccess",
      "Effect": "Allow",
      "Action": [
        "ecr:DescribeRepositories",
        "ecr:CreateRepository",
        "ecr:BatchCheckLayerAvailability",
        "ecr:BatchGetImage",
        "ecr:CompleteLayerUpload",
        "ecr:InitiateLayerUpload",
        "ecr:PutImage",
        "ecr:UploadLayerPart"
      ],
      "Resource": "arn:aws:ecr:ap-southeast-2:403351349592:repository/springboot-app-ecr-lab"
    }
  ]
}
```

**Permission summary:**
- `ecr:GetAuthorizationToken` – Authenticate Docker with Amazon ECR.
- Repository permissions – Access the specified repository and upload or retrieve Docker images.

## 3. Configure AWS CLI

Configure AWS credentials using the IAM user's access key:

```powershell
aws configure
```

Verify the AWS identity:

```powershell
aws sts get-caller-identity
```

## 4. Authenticate Docker with ECR

Log in to the Amazon ECR registry:

```powershell
cmd.exe /c "aws ecr get-login-password --region ap-southeast-2 | docker login --username AWS --password-stdin 403351349592.dkr.ecr.ap-southeast-2.amazonaws.com"
```

## 5. Tag the Docker Image

Tag the existing Docker Hub image with the ECR repository URI:

```powershell
docker tag htetoowaiyan12/springboot-app-with-docker-compose:latest 403351349592.dkr.ecr.ap-southeast-2.amazonaws.com/springboot-app-ecr-lab:latest
```

## 6. Push the Image to ECR

Push the tagged image to the private ECR repository:

```powershell
docker push 403351349592.dkr.ecr.ap-southeast-2.amazonaws.com/springboot-app-ecr-lab:latest
```

Verify the uploaded image in the AWS ECR Console.

## 7. Configure Docker Compose

Use the ECR image for the backend service instead of building it locally.

```yaml
services:
  backend:
    image: 403351349592.dkr.ecr.ap-southeast-2.amazonaws.com/springboot-app-ecr-lab:latest
```

MySQL and Redis remain separate services in `compose.yaml`.

## 8. Pull and Deploy

Pull the backend image from ECR:

```powershell
docker compose pull backend
```

Start all services:

```powershell
docker compose up -d
```

Check container status:

```powershell
docker compose ps
```

View backend logs:

```powershell
docker compose logs --tail 60 backend
```

## 9. Verify Application

Check the Spring Boot health endpoint:

```powershell
Invoke-RestMethod http://localhost:5001/actuator/health
```

Expected result:

```json
{
  "status": "UP"
}
```

## 10. Useful Docker Compose Commands

Stop running containers:

```powershell
docker compose stop
```

Start stopped containers:

```powershell
docker compose start
```

Remove containers and network:

```powershell
docker compose down
```

Pull updated images:

```powershell
docker compose pull
```

Follow backend logs:

```powershell
docker compose logs -f backend
```

## Notes

- Configure application environment variables in `.env`.
- Keep `.env` and AWS credentials out of GitHub.
- Use `.env.example` as the configuration template.
- Never commit AWS access keys or