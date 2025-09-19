import styles from "./InputBox.module.css";

interface inputFields {
  type?: string;
  size?: "sm" | "md";
  placeholder?: string;
  disabled?: boolean; //This is for disabled styling
}

function InputBox({
  type = "text",
  size = "sm",
  placeholder = "",
  disabled = false,
}: inputFields) {
  const inputStyles = [
    styles.input,
    styles[size],
    disabled ? styles.disabled : " ",
  ]
    .filter(Boolean)
    .join(" ");

  return (
    <>
      <input
        className={inputStyles}
        type={type}
        placeholder={placeholder}
        disabled={disabled}
      />
    </>
  );
}

//Example usage
/*    <InputBox
      label="Email"
      placeholder="Key in your email"
      disabled={false}
    ></InputBox>
*/

export default InputBox;
