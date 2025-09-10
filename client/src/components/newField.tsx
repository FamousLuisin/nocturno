import type { UseFormReturn } from "react-hook-form";
import {
  FormField,
  FormControl,
  FormDescription,
  FormItem,
  FormLabel,
  FormMessage,
} from "./ui/form";
import { Input } from "./ui/input";

type formProps = {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  form: UseFormReturn<any, any>;
  label: string;
  placeholder: string;
  description?: string;
  name: string;
  type?: string;
};

export default function NewField(props: formProps) {
  return (
    <FormField
      control={props.form.control}
      name={props.name}
      render={({ field }) => (
        <FormItem>
          <FormLabel>{props.label}</FormLabel>
          <FormControl>
            <Input
              type={props.type ? props.type : "text"}
              placeholder={props.placeholder}
              {...field}
            />
          </FormControl>
          <FormDescription>{props.description}</FormDescription>
          <FormMessage />
        </FormItem>
      )}
    />
  );
}
