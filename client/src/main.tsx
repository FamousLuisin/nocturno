import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import { createBrowserRouter } from "react-router";
import { RouterProvider } from "react-router";
import Layout from "./components/layout.tsx";
import App from "./routes/app.tsx";
import { ThemeProvider } from "./context/theme-provider.tsx";
import { AuthProvider } from "./context/auth-provider.tsx";
import Register from "./routes/register.tsx";
import "react-image-crop/dist/ReactCrop.css";
import Login from "./routes/login.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    Component: Layout,
    children: [
      {
        path: "/",
        Component: App,
      },
      {
        path: "/signup",
        Component: Register,
      },
      {
        path: "/signin",
        Component: Login
      }
    ],
  },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <ThemeProvider>
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>
    </ThemeProvider>
  </StrictMode>
);
