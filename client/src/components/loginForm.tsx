import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import z from "zod";
import { Form } from "./ui/form";
import NewField from "./newField";
import { LoaderCircle, LogIn } from "lucide-react";
import { Button } from "./ui/button";
import { useNavigate } from "react-router";
import { useState } from "react";

const formSchema = z.object({
    email: z.email(),
    password: z.string()
})

export default function LoginForm(){
    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            email: "",
            password: ""
        }
    })
    const navigate = useNavigate()
    const [loading, setLoading] = useState<Boolean>(false)

    async function onSubmit(values: z.infer<typeof formSchema>){
        setLoading(true)

        const url = "http://localhost:8080/auth/login"

        const req = new Request(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify({
                email: values.email,
                password: values.password
            })
        })

        try {
            const res = await fetch(req)

            if(!res.ok){
                throw new Error("error when logging in")
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
                <LogIn size={36} />
                <span className="text-2xl text-primary font-semibold">Register</span>
            </div>
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)} className="flex flex-col space-y-4 min-w-3xs p-4">
                    <NewField form={form} label="email" name="email" placeholder="email..."/>
                    <NewField form={form} label="password" name="password" placeholder="password..." type="password"/>
                    <Button type="submit">{loading ? <span className="animate-spin">< LoaderCircle /> </span> : <span>Submit</span>}</Button>
                </form>
            </Form>
        </div>
    )
}