# Use the official Nginx base image
FROM nginx:latest

# Expose port 80 for Nginx
EXPOSE 80

# Start Nginx (default CMD in the Nginx base image)
CMD ["nginx", "-g", "daemon off;"]
