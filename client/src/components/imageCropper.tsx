import type { ControllerRenderProps, UseFormReturn } from "react-hook-form";
import {
  centerCrop,
  convertToPixelCrop,
  makeAspectCrop,
  ReactCrop,
  type Crop,
} from "react-image-crop";
import {
  FormField,
  FormControl,
  FormItem,
  FormMessage,
  FormLabel,
  FormDescription,
} from "./ui/form";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Input } from "./ui/input";
import { useRef, useState } from "react";
import "react-image-crop/dist/ReactCrop.css";
import { Button } from "./ui/button";
import setCanvasPreview from "@/scripts/setCanvasPreview";
import { UploadIcon } from "lucide-react";

type formProps = {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  form: UseFormReturn<any, any>;
  label: string;
  description?: string;
  name: string;
};

const ACCEPTED_IMAGE_TYPES = ["image/jpeg", "image/jpg", "image/png"];
const MIN_WIDTH = 150;
const ASPECT_RATIO = 1;

export default function ImageCropper(props: formProps) {
  const imageRef = useRef<HTMLImageElement | null>(null);
  const previewCanvasRef = useRef<HTMLCanvasElement | null>(null);
  const [imageSrc, setImgSrc] = useState("");
  const [crop, setCrop] = useState<Crop>();
  const [open, setOpen] = useState(false);

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const onSelectFile = (e: any, onChange: (val: File) => void) => {
    const file = e.target.files?.[0];
    if (!file) return;

    onChange(file?.name);

    const reader = new FileReader();
    reader.addEventListener("load", () => {
      const imageElement = new Image();
      const imageUrl = reader.result?.toString() || "";
      imageElement.src = imageUrl;

      imageElement.addEventListener("load", () => {
        const { naturalWidth, naturalHeight } = imageElement;
        if (naturalWidth < MIN_WIDTH || naturalHeight < MIN_WIDTH) {
          return setImgSrc("");
        }
      });

      setImgSrc(imageUrl);
    });

    reader.readAsDataURL(file);
    setOpen(true);
  };

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const onImageLoad = (e: any) => {
    const { width, height } = e.currentTarget;
    const cropWidthInPercent = (MIN_WIDTH / width) * 100;

    const crop = makeAspectCrop(
      {
        unit: "%",
        width: cropWidthInPercent,
      },
      ASPECT_RATIO,
      width,
      height
    );

    const centeredCrop = centerCrop(crop, width, height);
    setCrop(centeredCrop);
  };

  const updateFieldWithCanvasBase64 = (
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    field: ControllerRenderProps<any, any>
  ) => {
    if (!previewCanvasRef.current || !crop) return;

    const canvas = previewCanvasRef.current;

    const base64 = canvas.toDataURL("image/png");

    field.onChange(base64);
  };

  return (
    <FormField
      control={props.form.control}
      name={props.name}
      render={({ field }) => (
        <FormItem>
          <FormControl>
            <div className="flex flex-col gap-2">
              <FormLabel>{props.label}</FormLabel>
              <Input
                type="file"
                accept={ACCEPTED_IMAGE_TYPES.join(",")}
                onChange={(e) => onSelectFile(e, field.onChange)}
                onBlur={field.onBlur}
                name={field.name}
                ref={field.ref}
                id="file-upload"
                className="hidden"
              />

              <div
                className="relative w-[150px] h-[150px] cursor-pointer"
                onClick={() => document.getElementById("file-upload")?.click()}
              >
                <canvas
                  ref={previewCanvasRef}
                  className="border rounded-xl w-full h-full"
                />
                {!imageSrc && (
                  <div className="absolute inset-0 flex items-center justify-center pointer-events-none">
                    <UploadIcon className="w-8 h-8 text-gray-400" />
                  </div>
                )}
              </div>
            </div>
          </FormControl>

          <FormDescription>choose picture 150 x 150 px</FormDescription>
          <FormMessage />

          {imageSrc && (
            <Dialog open={open} onOpenChange={setOpen}>
              <DialogContent>
                <DialogHeader className="flex flex-col items-center justify-center">
                  <DialogTitle>Image Crop</DialogTitle>
                  <DialogDescription className="flex flex-col gap-2" asChild>
                    <div>
                      <ReactCrop
                        crop={crop}
                        onChange={(_pixelCrop, percentCrop) =>
                          setCrop(percentCrop)
                        }
                        circularCrop
                        keepSelection
                        aspect={ASPECT_RATIO}
                        minWidth={MIN_WIDTH}
                      >
                        <img
                          src={imageSrc}
                          ref={imageRef}
                          alt="Preview"
                          style={{ maxHeight: "50vh" }}
                          onLoad={onImageLoad}
                        />
                      </ReactCrop>
                      <Button
                        onClick={() => {
                          if (
                            !imageRef.current ||
                            !previewCanvasRef.current ||
                            !crop
                          )
                            return;

                          setCanvasPreview(
                            imageRef.current,
                            previewCanvasRef.current,
                            convertToPixelCrop(
                              crop,
                              imageRef.current.width,
                              imageRef.current.height
                            )
                          );

                          updateFieldWithCanvasBase64(field);

                          setOpen(false);
                        }}
                      >
                        Crop Image
                      </Button>
                    </div>
                  </DialogDescription>
                </DialogHeader>
              </DialogContent>
            </Dialog>
          )}
        </FormItem>
      )}
    />
  );
}
