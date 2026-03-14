import Button from "../btn/Button";
import styles from "./Modal.module.css";
import ReactDom from "react-dom";

export default function Modal({ message, setShowModal }) {
  return ReactDom.createPortal(
    <>
      <div className={styles.overlay}>
        <div className={styles.modal}>
          <div id={styles.header} />
          <div id={styles.message}>{message}</div>
          <div id={styles.closeBtn}>
            <Button
              size={"sm"}
              text={"ok"}
              onClick={() => setShowModal(false)}
            ></Button>
          </div>
        </div>
      </div>
    </>,
    document.body,
  );
}
