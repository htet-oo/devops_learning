# AWS EC2 - Ubuntu Web Server with NGINX

## Overview

This project documents how to launch an AWS EC2 instance, connect to Ubuntu using SSH, install and manage NGINX, and make a web server publicly accessible through an AWS Security Group.

The goal is to practice AWS EC2 fundamentals, Linux server administration, networking, and basic cloud security.

## Architecture

```text
                 Internet
                    |
              Public IPv4
                    |
             Internet Gateway
                    |
               Public Subnet
                    |
               EC2 Instance
                 Ubuntu
                    |
                  NGINX
                    |
                HTTP :80
```

## Technologies Used

- Amazon EC2
- Ubuntu Server 24.04 LTS
- NGINX
- Amazon VPC
- AWS Security Groups
- SSH
- Windows PowerShell

## 1. Launch an EC2 Instance

Example configuration used for this learning exercise:

| Setting | Example |
|---|---|
| Operating system | Ubuntu Server 24.04 LTS |
| Instance type | Micro instance |
| AWS Region | Asia Pacific (Sydney) |
| Key pair | `h.o.w.y.pem` |
| Network | Default VPC / public subnet |
| Public IPv4 | Enabled |
| Storage | 8 GiB gp3 |

> Instance type, storage size, and pricing eligibility depend on the AWS account and Region. Check the current pricing before launching resources.

## 2. Configure the SSH Private Key on Windows

In Windows PowerShell, move to the directory containing the private key:

```powershell
cd C:\devops\aws
```

Remove inherited permissions:

```powershell
icacls .\h.o.w.y.pem /inheritance:r
```

Grant read permission to the current Windows user:

```powershell
icacls .\h.o.w.y.pem /grant:r "$($env:USERNAME):R"
```

Check the permissions:

```powershell
icacls .\h.o.w.y.pem
```

Keep the `.pem` file private. Never upload it to GitHub or commit it to a repository.

## 3. Connect to EC2 Using SSH

From Windows PowerShell:

```powershell
ssh -i .\h.o.w.y.pem ubuntu@YOUR_PUBLIC_IP
```

Replace `YOUR_PUBLIC_IP` with the instance's current public IPv4 address.

## 4. Update Ubuntu Package Information

Inside the Ubuntu instance:

```bash
sudo apt update
```

This refreshes the package information from the configured Ubuntu repositories.

## 5. Install NGINX

```bash
sudo apt install nginx -y
```

## 6. Manage the NGINX Service

Check the service status:

```bash
sudo systemctl status nginx
```

Start NGINX immediately:

```bash
sudo systemctl start nginx
```

Enable NGINX to start automatically after reboot:

```bash
sudo systemctl enable nginx
```

Restart NGINX:

```bash
sudo systemctl restart nginx
```

Check whether it is running:

```bash
sudo systemctl is-active nginx
```

Expected output:

```text
active
```

## 7. Test NGINX Locally

From the Ubuntu terminal:

```bash
curl http://localhost
```

The response should contain the default NGINX welcome page, including:

```html
<h1>Welcome to nginx!</h1>
```

This verifies that NGINX is responding locally on the instance.

## 8. Configure the AWS Security Group

Configure inbound rules for the EC2 instance:

| Type | Protocol | Port | Source | Purpose |
|---|---|---:|---|---|
| SSH | TCP | 22 | My IP (`/32`) | Remote administration from an allowed IP |
| HTTP | TCP | 80 | `0.0.0.0/0` | Public IPv4 web access |

### Security notes

- Keep SSH (port 22) restricted to your current public IP whenever possible.
- HTTP (port 80) is public in this exercise so visitors can access the web server.
- `0.0.0.0/0` means all IPv4 addresses. It does not grant access to every service or port; only traffic allowed by the rules can pass.
- Do not expose administrative services or databases publicly unless there is a specific, secured requirement.

## 9. Access the Website

Open a browser and visit:

```text
http://YOUR_PUBLIC_IP
```

Use `http://` for this exercise. HTTPS requires a separate TLS certificate and HTTPS configuration.

The default **Welcome to nginx!** page should appear.

## 10. Troubleshooting

### SSH connection times out

Check:

- The instance is running and has passed its status checks.
- The public IPv4 address is current.
- The Security Group allows TCP port 22 from your public IP.
- The instance is in a public subnet with a route to an Internet Gateway.
- Network ACL rules and local network restrictions are not blocking the connection.

From Windows PowerShell:

```powershell
Test-NetConnection YOUR_PUBLIC_IP -Port 22
```

### Website does not load

Check NGINX inside Ubuntu:

```bash
sudo systemctl status nginx
curl http://localhost
```

Check HTTP connectivity from Windows PowerShell:

```powershell
Test-NetConnection YOUR_PUBLIC_IP -Port 80
```

Also verify that the Security Group allows TCP port 80 and that the subnet has a working public route.

## 11. Clean Up AWS Resources

When the learning exercise is finished:

1. Terminate the EC2 instance if it is no longer needed.
2. Check whether any EBS volumes remain.
3. Check for allocated Elastic IP addresses.
4. Review snapshots and other retained resources.
5. Review AWS Billing and Cost Explorer.

Terminating an instance stops its compute charges, but retained EBS volumes, snapshots, public IPv4 addresses, and other billable resources may continue to incur charges.

## What I Learned

- Launching and configuring an EC2 instance.
- Understanding AMIs and instance types.
- Connecting to Ubuntu through SSH.
- Managing Linux services with `systemctl`.
- Installing and testing NGINX.
- Understanding public IPv4 addresses and HTTP.
- Understanding VPCs, public subnets, and Internet Gateways.
- Configuring inbound Security Group rules.
- Distinguishing SSH access from public HTTP access.
- Troubleshooting network connectivity.
- Cleaning up AWS resources to reduce unnecessary costs.

## Future Improvements

- Configure a domain name.
- Enable HTTPS with a trusted TLS certificate.
- Replace the default NGINX page with a custom website.
- Automate NGINX installation using EC2 User Data.
- Attach an IAM Role to EC2 for AWS service access.
- Deploy a Dockerized application.
- Automate deployment with GitHub Actions.

---

**Project:** AWS EC2 Ubuntu Web Server  
**Focus:** Cloud Infrastructure / Linux / Networking / DevOps
