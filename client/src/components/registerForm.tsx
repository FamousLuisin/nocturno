import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Form } from "@/components/ui/form";
import { Button } from "./ui/button";
import ImageCropper from "./imageCropper";
import NewField from "./newField";
import { LoaderCircle, UserRoundPlus } from "lucide-react";
import { useNavigate } from "react-router";
import { useState } from "react";

const formSchema = z
  .object({
    username: z.string().min(2, {
      error: "Username must be at least 2 characters.",
    }),
    image: z.string().min(1, { error: "Mandatory image" }),
    displayname: z
      .string()
      .min(1, { error: "Displayname must be at least 2 characters." }),
    birth: z.iso.date(),
    email: z.email({ error: "Email not is valid" }),
    password: z
      .string()
      .min(8, { error: "Password must be at least 8 characters." }),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    error: "Password don't match",
    path: ["confirmPassword"],
  })
  .refine(
    (data) => {
      const birth = new Date(data.birth);
      const current = new Date();

      if (
        current.getFullYear() - birth.getFullYear() < 15 ||
        birth.getFullYear() < 1900
      ) {
        return false;
      }

      return true;
    },
    { error: "Invalid date", path: ["birth"] }
  );

export default function RegisterForm() {
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      image: "",
      birth: "",
      password: "",
      confirmPassword: "",
      displayname: "",
      email: ""
    },
  });
  const navigate = useNavigate()
  const [loading, setLoading] = useState<Boolean>(false)

  async function onSubmit(values: z.infer<typeof formSchema>) {
    setLoading(true)

    const url = "http://localhost:8080/auth/register"

    const req = new Request(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      credentials:"include",
      body: JSON.stringify({
        username: values.username,
        displayName: values.displayname,
        birthday: values.birth,
        password: values.password,
        confirmPassword: values.confirmPassword,
        email: values.email,
        picture: values.image
      })
    })

    try {
      const response = await fetch(req)

      if(!response.ok){
        throw new Error("error when registering")
      }

      navigate("/")
    } catch (error) {
      console.log(error)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="border-1 border-zinc-500 rounded-2xl min-w-sm flex flex-col gap-2">
      <div className="flex items-center gap-2 p-4 border-b">
        <UserRoundPlus size={36} />
        <span className="text-2xl text-primary font-semibold">Register</span>
      </div>

      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="flex flex-col space-y-4 min-w-3xs p-4"
        >
          <ImageCropper
            form={form}
            name="image"
            label="Image"
            description="Your picture"
          />
          <NewField
            name="username"
            form={form}
            label="Username"
            placeholder="username"
          />
          <NewField name="name" form={form} label="Name" placeholder="name" />
          <NewField
            name="displayname"
            form={form}
            label="Display name"
            placeholder="displayname"
          />
          <NewField
            name="email"
            form={form}
            label="Email"
            placeholder="email@email.com"
          />
          <NewField
            name="birth"
            form={form}
            label="Birthday"
            placeholder="birthday"
            type="date"
          />
          <NewField
            name="password"
            form={form}
            label="Password"
            placeholder="password"
            type="password"
          />
          <NewField
            name="confirmPassword"
            form={form}
            label="Confirm Password"
            placeholder="confirm password"
            type="password"
          />
          <Button type="submit">{loading ? <span className="animate-spin">< LoaderCircle /> </span> : <span>Submit</span>}</Button>
        </form>
      </Form>
    </div>
  );
}
