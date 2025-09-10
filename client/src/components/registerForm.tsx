import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Form } from "@/components/ui/form";
import { Button } from "./ui/button";
import ImageCropper from "./imageCropper";
import NewField from "./newField";
import { UserRoundPlus } from "lucide-react";

const formSchema = z
  .object({
    username: z.string().min(2, {
      error: "Username must be at least 2 characters.",
    }),
    image: z.string().min(1, { error: "Mandatory image" }),
    name: z.string().min(1, { error: "Name must be at least 2 characters." }),
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
    },
  });

  function onSubmit(values: z.infer<typeof formSchema>) {
    console.log(values);
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
          <Button type="submit">Submit</Button>
        </form>
      </Form>
    </div>
  );
}
